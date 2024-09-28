//
// Created by arjun on 6/19/24.
//
#include <list>
#include <vector>
#include <string.h>
#include <pthread.h>
#include <thread>
#include <cstring>
#include <jni.h>
#include <unistd.h>
#include <fstream>
#include <iostream>
#include <dlfcn.h>
#include "Includes/Logger.h"
#include "Socket/Socket.h"
#include "Socket/response.h"
#include "Includes/obfuscate.h"
#include "Includes/Utils.h"
#include "KittyMemory/MemoryPatch.h"

#define targetLibName "libil2cpp.so"
#include "Includes/Macros.h"

void *dl = nullptr;


bool stopZ = false;
void (*_stopZombie)(void *player);
void StopZombie(void *player) {
    if(player != nullptr) {
        if(stopZ) {
            return;
        }
    }
    _stopZombie(player);
}

void *hack_thread(void *) {
    do {
        sleep(1);
    } while (!isLibraryLoaded(targetLibName));

    LOGI("%s LOADED", targetLibName);
    HOOK("0x85C3C8", StopZombie, _stopZombie);
    return nullptr;
}

void Changes(int featNum, int value, bool boolean) {
    switch (featNum) {
        case 2:
            LOGD("TOGGLE %d", boolean);
            stopZ = boolean;
            break;
    }
}

void *CreateServer(void *) {
    if (InitServer() == 0) {
        if (server.Accept()) {
            Request request{};
            while (server.receive((void*)&request) > 0) {
                Response response{};

                if (request.Mode == Mode::InitMode) {
                    response.Success = true;
                }

                if (request.Mode == Mode::StopMode) {
                    response.Success = true;
                    server.send((void *) &response, sizeof(response));
                    server.Close();
                }


                response.Success = true;
                Changes(request.Mode, request.value, request.boolean);
                server.send((void*)& response, sizeof(response));
            }
        }
    }
    return nullptr;
}


__attribute__((constructor))
void lib_main() {
    pthread_t t_server;
    pthread_t t_hack;
    pthread_create(&t_server, nullptr, CreateServer, nullptr);
    pthread_create(&t_hack, nullptr, hack_thread, nullptr);
}


# Better Inject

Android Shared Library Mod Injector (Ptrace).

### Support:
- Android 5 ~ 14
- ABI arm, arm64, x86, x86_64
- Emulator Support (libhoudini.so or libndk_translation.so)
- Bypass android linker namespace restrictions
- memfd dlopen support
- Auto launch

### How To use

Edit the ```package_name``` in ```Client/Client.cpp``` as per your need

Add ``Features List`` in ```Client/Client.cpp```
and Add ```mods``` in ```Server/Server.cpp```. Also you can follow all wiki lie ```LGL Mod Menu``` but, you have to put ```cases``` in ```Server/Server.cpp```


For JavaVM support, you can add following code to ```Server/Server.cpp```

```cpp
extern "C" jint JNIEXPORT JNI_OnLoad(JavaVM* vm, void *key)
{
    // key 1337 is passed by injector
    if (key != (void*)1337)
        return JNI_VERSION_1_6;

    KITTY_LOGI("JNI_OnLoad called by injector.");

    JNIEnv *env = nullptr;
    if (vm->GetEnv((void**)&env, JNI_VERSION_1_6) == JNI_OK)
    {
        KITTY_LOGI("JavaEnv: %p.", env);
        // ...
    }
    
    std::thread(thread_function).detach();
    
    return JNI_VERSION_1_6;
}
```

YOU MUST HAVE ANDROID STUDIO TO BUILD THE PROJECT, LATER I WILL FIX THE BUILD FOR AIDE.

# Credits
- ```Android Mod Menu```: https://github.com/LGLTeam/Android-Mod-Menu
- ```AndKittyInjector```: https://github.com/MJx0/AndKittyInjector
- ```KittyMemory```: https://github.com/MJx0/KittyMemory
- ```SubstraceHook```: https://github.com/Octowolve/Substrate-Hooking-Example
- ```And64inlineHook```: https://github.com/Rprop/And64InlineHook


## Authors

- [@nepmods](https://www.github.com/nepmods)


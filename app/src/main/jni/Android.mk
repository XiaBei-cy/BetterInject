LOCAL_PATH := $(call my-dir)


include $(CLEAR_VARS)
LOCAL_MODULE := GlobalInject
LOCAL_SRC_FILES := Client/libs/$(TARGET_ARCH_ABI)/libinject.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := MyLibName
LOCAL_CFLAGS := -w -s -Wno-error=format-security -fvisibility=hidden -fpermissive -fexceptions
LOCAL_CPPFLAGS := -w -s -Wno-error=format-security -fvisibility=hidden -Werror -std=c++17
LOCAL_CPPFLAGS += -Wno-error=c++11-narrowing -fpermissive -Wall -fexceptions
LOCAL_LDFLAGS += -Wl,--gc-sections,--strip-all,-llog
LOCAL_LDLIBS := -llog -landroid -lEGL -lGLESv2
LOCAL_ARM_MODE := arm
LOCAL_C_INCLUDES += $(LOCAL_PATH)

# Here you add the cpp file to compile
LOCAL_SRC_FILES := Client/Client.cpp \
	Client/Sources/Socket/Socket.cpp \

include $(BUILD_SHARED_LIBRARY)


include $(CLEAR_VARS)
LOCAL_MODULE    := Server
LOCAL_CFLAGS := -w -s -Wno-error=format-security -fvisibility=hidden -fpermissive -fexceptions
LOCAL_CPPFLAGS := -w -s -Wno-error=format-security -fvisibility=hidden -Werror -std=c++17
LOCAL_CPPFLAGS += -Wno-error=c++11-narrowing -fpermissive -Wall -fexceptions
LOCAL_LDFLAGS += -Wl,--gc-sections,--strip-all,-llog
LOCAL_LDLIBS := -llog -landroid -lEGL -lGLESv2
LOCAL_ARM_MODE := arm

LOCAL_C_INCLUDES += $(LOCAL_PATH)
LOCAL_C_INCLUDES += $(LOCAL_PATH)/Server/Sources

LOCAL_SRC_FILES := Server/Server.cpp \
	Server/Sources/Substrate/hde64.c \
	Server/Sources/Substrate/SubstrateDebug.cpp \
	Server/Sources/Substrate/SubstrateHook.cpp \
	Server/Sources/Substrate/SubstratePosixMemory.cpp \
	Server/Sources/Substrate/SymbolFinder.cpp \
	Server/Sources/KittyMemory/KittyMemory.cpp \
	Server/Sources/KittyMemory/MemoryPatch.cpp \
    Server/Sources/KittyMemory/MemoryBackup.cpp \
    Server/Sources/KittyMemory/KittyUtils.cpp \
	Server/Sources/And64InlineHook/And64InlineHook.cpp \
	Server/Sources/Socket/Socket.cpp \


include $(BUILD_SHARED_LIBRARY)



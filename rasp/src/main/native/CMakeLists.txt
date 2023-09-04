cmake_minimum_required(VERSION 3.18.1)

project("rasp")

add_subdirectory(io)

include_directories(io
        jni
        check
        utils)

add_library(
        rasp

        SHARED

        rasp.cpp
        check/check_frida.cpp
        check/check_frida.h
        check/check_dual_app.cpp
        check/check_dual_app.h
        check/check_xposed.cpp
        check/check_xposed.h
        check/check_emulator.cpp
        check/check_emulator.h
        check/check_mem_dump.cpp
        check/check_mem_dump.h

        jni/utils/JavaObjectPointer.cpp
        jni/utils/JStringUtils.cpp
        jni/native/JavaNativeMethod.cpp
        jni/core/JNIEnvironment.cpp
        jni/core/JavaCustomClass.cpp

        utils/Utils.cpp

        xposeddetector/art.h
        xposeddetector/classloader.cpp
        xposeddetector/classloader.h
        xposeddetector/find_name.cpp
        xposeddetector/find_name.h
        xposeddetector/hash.cpp
        xposeddetector/hash.h
        xposeddetector/plt.h
        xposeddetector/plt.c
        xposeddetector/xposed.cpp
        xposeddetector/xposed.h
)


find_library(
        log-lib
        log)

target_link_libraries(
        rasp

        io
        ${log-lib})
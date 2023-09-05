#include <jni.h>
#include <string>
#include <thread>

#include "check/check_frida.h"
#include "check/check_dual_app.h"
#include "check/check_xposed.h"
#include "jni/JNIHelper.hpp"
#include "xposeddetector/xposed.h"
#include "check/check_mem_dump.h"
#include "check/check_emulator.h"

#define JNI_CLASS_NAME "com/example/rasp/NativeLib"

static jstring frida_guard(JNIEnv *env, jclass clazz) {
    jh::JNIEnvironmentGuarantee jniEnvironmentGuarantee;
    FridaSecure fridaSecure;
    fridaSecure.check();

    return jh::createJString("Frida Secure");
}

static jstring xposed_gaurd(JNIEnv *env, jclass clazz) {
    jh::JNIEnvironmentGuarantee jniEnvironmentGuarantee;
    XposedSecurity xposedSecurity;
    if (xposedSecurity.get_xposed_status(env, android_get_device_api_level()) == NO_XPOSED) {
        return jh::createJString("XPOSED Secure");
    } else if (xposed_status == FOUND_XPOSED) {
        return jh::createJString("FOUND_XPOSED");
    } else if (xposed_status == ANTIED_XPOSED) {
        return jh::createJString("XPOSED_SECURED");
    } else if (xposed_status == CAN_NOT_ANTI_XPOSED) {
        return jh::createJString("CAN_NOT_SECURE_XPOSED");
    }

}

static jstring memory_dump_guard(JNIEnv *env, jclass clazz) {

    std::thread t(MemoryDumpSecurity::detect_memory_dump_loop, nullptr);
    t.detach();

    return jh::createJString("Memory Dump Secure");
}

static jstring emulator_guard(JNIEnv *env, jclass clazz) {
    EmulatorSecurity emulatorSecurity;

    return jh::createJString(emulatorSecurity.check());
}

static jstring dual_app_guard(JNIEnv *env, jclass clazz) {
    jh::JNIEnvironmentGuarantee jniEnvironmentGuarantee;
    DualAppSecurity dualAppSecurity;

    return jh::createJString(dualAppSecurity.check());
}


JNIEXPORT jint

JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {

    jh::onLoad(vm);

    (void) reserved;

    if (__predict_false(nullptr == vm)) return JNI_ERR;

    JNIEnv *env;
    if (__predict_false(JNI_OK != vm->GetEnv((void **) &env, JNI_VERSION_1_6))) return JNI_ERR;
    if (__predict_false(nullptr == env)) return JNI_ERR;

    jclass cls;
    if (__predict_false(nullptr == (cls = env->FindClass(JNI_CLASS_NAME)))) return JNI_ERR;

    JNINativeMethod m[] =
            {
                    {"FridaGuard",    "()Ljava/lang/String;", (void *) frida_guard},
                    {"XposedGuard",   "()Ljava/lang/String;", (void *) xposed_gaurd},
                    {"MemDumpGuard",  "()Ljava/lang/String;", (void *) memory_dump_guard},
                    {"EmulatorGuard", "()Ljava/lang/String;", (void *) emulator_guard},
                    {"DualAppGuard",  "()Ljava/lang/String;", (void *) dual_app_guard},
            };

    if (__predict_false(0 != env->RegisterNatives(cls, m, sizeof(m) / sizeof(m[0]))))
        return JNI_ERR;


    return JNI_VERSION_1_6;
}
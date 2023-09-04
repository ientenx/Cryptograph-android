#include <jni.h>
#include <string>
#include <thread>

#include "check/check_frida.h"
#include "check/check_dual_app.h"
#include "check/check_xposed.h"
#include "JNIHelper/JNIHelper.hpp"
#include "xposeddetector/xposed.h"
#include "check/check_mem_dump.h"
#include "check/check_emulator.h"

#define JNI_CLASS_NAME "com/example/rasp/NativeLib"

static jstring anti_frida(JNIEnv *env, jclass clazz) {
    jh::JNIEnvironmentGuarantee jniEnvironmentGuarantee;
    AntiFrida antiFrida;
    antiFrida.check();

    return jh::createJString("Frida Secure");
}

static jstring anti_L_xposed(JNIEnv *env, jclass clazz) {
    jh::JNIEnvironmentGuarantee jniEnvironmentGuarantee;
    AntiXposed antiXposed;
    if (antiXposed.get_xposed_status(env, android_get_device_api_level()) == NO_XPOSED) {
        return jh::createJString("XPOSED Secure");
    } else if (xposed_status == FOUND_XPOSED) {
        return jh::createJString("FOUND_XPOSED");
    } else if (xposed_status == ANTIED_XPOSED) {
        return jh::createJString("ANTIED_XPOSED");
    } else if (xposed_status == CAN_NOT_ANTI_XPOSED) {
        return jh::createJString("CAN_NOT_ANTI_XPOSED");
    }

}

static jstring anti_mem_dump(JNIEnv *env, jclass clazz) {

    std::thread t(AntiMemDump::detect_memory_dump_loop, nullptr);
    t.detach();

    return jh::createJString("Memory Dump Secure");
}

static jstring anti_emulator(JNIEnv *env, jclass clazz) {
    AntiEmulator antiEmulator;

    return jh::createJString(antiEmulator.check());
}

static jstring anti_dual_app(JNIEnv *env, jclass clazz) {
    jh::JNIEnvironmentGuarantee jniEnvironmentGuarantee;
    AntiDualApp antiDualApp;

    return jh::createJString(antiDualApp.check());
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
                    {"Frida",    "()Ljava/lang/String;", (void *) anti_frida},
                    {"Xposed",   "()Ljava/lang/String;", (void *) anti_L_xposed},
                    {"MemDump",  "()Ljava/lang/String;", (void *) anti_mem_dump},
                    {"Emulator", "()Ljava/lang/String;", (void *) anti_emulator},
                    {"DualApp",  "()Ljava/lang/String;", (void *) anti_dual_app},
            };

    if (__predict_false(0 != env->RegisterNatives(cls, m, sizeof(m) / sizeof(m[0]))))
        return JNI_ERR;


    return JNI_VERSION_1_6;
}
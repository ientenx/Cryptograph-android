#include "check_xposed.h"
#include <jni.h>
#include "../xposeddetector/classloader.h"
#include "Utils.h"
#include "../jni/JNIHelper.hpp"
#include "../xposeddetector/xposed.h"

int xposed_status = NO_XPOSED;

int XposedSecurity::get_xposed_status(JNIEnv *env, int sdk) {
    jh::JNIEnvironmentGuarantee jniEnvironmentGuarantee;
    classloader::checkClassLoader((C_JNIEnv *) env, sdk);

    xposed::checkCallStack((C_JNIEnv *) env);

    if (xposed_status == NO_XPOSED) {
        LOGE("xposed_status == NO_XPOSED");
    }

    xposed::checkCallStack((C_JNIEnv *) env);

    return xposed_status;
}
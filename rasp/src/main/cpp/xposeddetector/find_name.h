#ifndef RASP_FIND_NAME_H
#define RASP_FIND_NAME_H


#include <jni.h>

class find_name {
public:
    static char *findObjectArrayName(C_JNIEnv *env, jobject clazz);

    static char *findStaticMapName(C_JNIEnv *env, jobject clazz);

    static char *findVoidStringName(C_JNIEnv *env, jclass clazz);

private:
    static char *findField(C_JNIEnv *env, jobject clazz, int staticFlag, jclass type);

};


#endif

#ifndef ANTI_ANDROID_ANTI_DUAL_APP_H
#define ANTI_ANDROID_ANTI_DUAL_APP_H

#include <string>
#include <jni.h>

class AntiDualApp {
public:
    std::string check();

private:
    std::string check_dual_app();
};


#endif

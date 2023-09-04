package com.example.cryptograph

import com.example.rasp.NativeLib.*

import android.app.Application
import android.util.Log
import com.example.rasp.rootdetection.RootDetection
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

@HiltAndroidApp
class App : Application() {
    val coroutineScope = CoroutineScope(CoroutineName("application-scope"))

    override fun onCreate() {
        super.onCreate()
        xcrash.XCrash.init(this)
        Frida()
        Xposed()
        MemDump()
        Emulator()
        DualApp()

//        val rootcheck = RootDetection()
//        if (rootcheck.isDeviceRooted()) Log.i("ROOT", "Device is Rooted")
    }
    override fun onTerminate() {
        super.onTerminate()
        coroutineScope.cancel()
    }
}
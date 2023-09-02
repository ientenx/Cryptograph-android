package com.example.cryptograph

import com.tg.android.anti.NativeLib.*

import android.app.Application
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
        AntiFrida()
        AntiXposed()
        AntiRoot()
        AntiDebug()
        AntiMemDump()
        AntiEmulator()
        AntiDualApp()
    }
    override fun onTerminate() {
        super.onTerminate()
        coroutineScope.cancel()
    }
}
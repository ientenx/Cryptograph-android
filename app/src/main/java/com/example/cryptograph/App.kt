package com.example.cryptograph


import android.app.Application
import com.example.rasp.NativeLib.DualAppGuard
import com.example.rasp.NativeLib.EmulatorGuard
import com.example.rasp.NativeLib.FridaGuard
import com.example.rasp.NativeLib.MemDumpGuard
import com.example.rasp.NativeLib.XposedGuard
import com.example.rasp.emulatordetection.DeviceState
import com.example.rasp.emulatordetection.EmulatorDetector
import com.example.rasp.rootdetection.RootDetector.isDeviceRooted
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@HiltAndroidApp
class App : Application() {
    val coroutineScope = CoroutineScope(CoroutineName("application-scope"))

    private val emulatorDetector: EmulatorDetector = EmulatorDetector.Builder(this)
        .checkSensors()
        .checkProperties()
        .build()


    override fun onCreate() {
        super.onCreate()

        FridaGuard()
        XposedGuard()
        MemDumpGuard()
        EmulatorGuard()
        DualAppGuard()

        if (isDeviceRooted()) println("Device was root!") else println("Device isn't root!")

        coroutineScope.launch {
            val state = emulatorDetector.getState()
            println("is device an emulator ${state is DeviceState.Emulator}")
        }

    }
    override fun onTerminate() {
        super.onTerminate()
        coroutineScope.cancel()
    }
}
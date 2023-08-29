package com.example.cryptograph

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

@HiltAndroidApp
class App : Application() {
    val coroutineScope = CoroutineScope(CoroutineName("application-scope"))

    override fun onTerminate() {
        super.onTerminate()
        coroutineScope.cancel()
    }
}
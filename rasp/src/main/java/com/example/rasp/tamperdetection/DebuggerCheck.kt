package com.example.rasp.tamperdetection

import android.os.Debug

fun guardDebugger(error: (() -> Unit) = {}, function: (() -> Unit)) {
    val isDebuggerAttached = Debug.isDebuggerConnected() || Debug.waitingForDebugger()
    if (!isDebuggerAttached) {
        function.invoke()
    } else {
        error.invoke()
    }
}
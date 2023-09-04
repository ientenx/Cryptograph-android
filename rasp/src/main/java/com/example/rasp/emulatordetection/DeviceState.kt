package com.example.rasp.emulatordetection

sealed class DeviceState {

    class Emulator(
        val source: VerdictSource
    ) : DeviceState()

    object NotEmulator : DeviceState()
}
package com.example.rasp.emulatordetection

sealed class DeviceState {

    class Emulator(
        val source: Source
    ) : DeviceState()

    object NotEmulator : DeviceState()
}
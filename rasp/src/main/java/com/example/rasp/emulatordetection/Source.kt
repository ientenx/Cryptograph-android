package com.example.rasp.emulatordetection

sealed class Source {

    class Properties(
        val suspectDeviceProperties: List<Pair<String, String>>
    ) : Source()

    class Sensors(
        val suspectSensorValues: List<FloatArray>
    ) : Source()
}
package com.example.rasp.emulatordetection.sings.sensor

import android.content.Context
import com.example.rasp.emulatordetection.DeviceState
import com.example.rasp.emulatordetection.EmulatorDetector
import com.example.rasp.emulatordetection.Source

internal class SensorEmulatorDetector(
    context: Context,
    sensorType: Int,
    private val eventsCount: Int
) : EmulatorDetector() {

    private val sensorDataProcessor = SensorDataValidator()
    private val sensorEventProducer = SensorEventProducer(context, sensorType)

    override suspend fun getState(): DeviceState {
        val sensorEvents = sensorEventProducer.getSensorEvents(eventsCount)
            .onFailure { return DeviceState.NotEmulator }
            .getOrThrow()

        return if (sensorDataProcessor.isEmulator(sensorEvents)) {
            DeviceState.Emulator(Source.Sensors(sensorEvents))
        } else {
            DeviceState.NotEmulator
        }
    }
}
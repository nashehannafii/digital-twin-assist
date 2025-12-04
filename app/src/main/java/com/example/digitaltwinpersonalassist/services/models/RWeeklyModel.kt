package com.example.digitaltwinpersonalassist.services.models

import java.io.Serializable

/**
 * Data model for weekly recap from the API.
 *
 * @property message Message or status from the API.
 * @property data Map of day keys to `DayData`.
 */
data class RWeeklyModel(
    var message: String?,
    var data: Map<String, DayData>?
): Serializable

/**
 * Representation of data for a single day in the weekly recap.
 *
 * @property dayName The day name.
 * @property averageHeartbeatRate The average heartbeat rate for that day.
 */
data class DayData(
    var dayName: String?,
    var averageHeartbeatRate: Float?
) : Serializable
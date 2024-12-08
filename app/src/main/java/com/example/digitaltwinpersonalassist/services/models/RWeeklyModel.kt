package com.example.digitaltwinpersonalassist.services.models

import java.io.Serializable

data class RWeeklyModel(
    var message: String?,
    var data: Map<String, DayData>?
): Serializable

data class DayData(
    var dayName: String?,
    var averageHeartbeatRate: Float?
) : Serializable
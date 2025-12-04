package com.example.digitaltwinpersonalassist.services.models

import java.io.Serializable

/**
 * Data model for daily recap per hour from the API.
 *
 * @property message Message or status from the API.
 * @property data Map structure with hour/label keys and per-hour metric values.
 */
data class RDailyModel(
    var message: String?,
    var data: Map<String, Map<String, Float>>?
): Serializable

package com.example.digitaltwinpersonalassist.services.models

import java.io.Serializable

data class RDailyModel(
    var message: String?,
    var data: Map<String, Map<String, Float>>?
): Serializable

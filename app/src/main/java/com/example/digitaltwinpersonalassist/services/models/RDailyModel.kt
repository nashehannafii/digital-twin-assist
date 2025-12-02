package com.example.digitaltwinpersonalassist.services.models

import java.io.Serializable

/**
 * Model data untuk rekap harian per jam dari API.
 *
 * @property message Pesan atau status dari API.
 * @property data Struktur peta dengan kunci jam/label dan nilai metrik per jam.
 */
data class RDailyModel(
    var message: String?,
    var data: Map<String, Map<String, Float>>?
): Serializable

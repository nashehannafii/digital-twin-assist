package com.example.digitaltwinpersonalassist.services.models

import java.io.Serializable

/**
 * Model data untuk rekap mingguan dari API.
 *
 * @property message Pesan atau status dari API.
 * @property data Peta hari -> data hari (`DayData`).
 */
data class RWeeklyModel(
    var message: String?,
    var data: Map<String, DayData>?
): Serializable

/**
 * Representasi data untuk satu hari dalam rekapan mingguan.
 *
 * @property dayName Nama hari.
 * @property averageHeartbeatRate Rata-rata detak jantung pada hari tersebut.
 */
data class DayData(
    var dayName: String?,
    var averageHeartbeatRate: Float?
) : Serializable
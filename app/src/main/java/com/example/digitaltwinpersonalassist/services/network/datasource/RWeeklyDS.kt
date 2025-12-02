package com.example.digitaltwinpersonalassist.services.network.datasource

import com.example.digitaltwinpersonalassist.services.models.DayData

/**
 * Abstraksi data source untuk rekap mingguan.
 * Implementasi harus memanggil `RWeeklyCallback` dengan data yang diperoleh atau
 * error bila gagal.
 */
interface RWeeklyDS {

    /**
     * Ambil data rekap mingguan untuk pekan dan bulan tertentu.
     */
    fun getData(week: String, month: String, callback: RWeeklyCallback)

    interface RWeeklyCallback {
        /**
         * Dipanggil saat data berhasil diambil.
         *
         * @param data Map dari kunci hari ke `DayData`.
         */
        fun onLoaded(
            data: Map<String, DayData>
        )
        /**
         * Dipanggil saat terjadi error.
         */
        fun onError(msg: String)
    }

}
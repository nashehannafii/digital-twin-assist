package com.example.digitaltwinpersonalassist.services.network.datasource

/**
 * Abstraksi data source untuk rekap harian per jam.
 */
interface RDailyDS {

    /**
     * Ambil data rekap harian untuk tanggal dan bulan tertentu.
     */
    fun getData(date: String, month: String, callback: RDailyCallback)

    interface RDailyCallback{
        /**
         * Dipanggil saat data berhasil diambil (dalam bentuk String).
         */
        fun onLoaded(msg: String)
        /**
         * Dipanggil saat terjadi error.
         */
        fun onError(msg: String)
    }
}
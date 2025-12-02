package com.example.digitaltwinpersonalassist.services.network.datasource

/**
 * Abstraksi data source untuk endpoint "hello".
 * Implementor harus memanggil callback ketika data berhasil atau terjadi error.
 */
interface HelloDS {

    /**
     * Ambil data hello secara asinkron dan kembalikan hasil melalui callback.
     */
    fun getData(callback: HelloCallback)

    interface HelloCallback{
        /**
         * Dipanggil saat data berhasil diambil.
         */
        fun onLoaded(msg: String)
        /**
         * Dipanggil saat terjadi error.
         */
        fun onError(msg: String)
    }
}
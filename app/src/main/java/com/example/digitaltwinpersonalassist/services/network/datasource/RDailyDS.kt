package com.example.digitaltwinpersonalassist.services.network.datasource

/**
 * Abstraction for the daily-per-hour recap data source.
 */
interface RDailyDS {

    /**
     * Fetch daily recap data for a given date and month.
     */
    fun getData(date: String, month: String, callback: RDailyCallback)

    interface RDailyCallback{
        /**
         * Called when data is successfully loaded (as a String).
         */
        fun onLoaded(msg: String)
        /**
         * Called when an error occurs.
         */
        fun onError(msg: String)
    }
}
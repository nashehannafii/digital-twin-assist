package com.example.digitaltwinpersonalassist.services.network.datasource

import com.example.digitaltwinpersonalassist.services.models.DayData

/**
 * Abstraction for the weekly recap data source.
 * Implementations should call `RWeeklyCallback` with data or an error.
 */
interface RWeeklyDS {

    /**
     * Fetch weekly recap data for a specific week and month.
     */
    fun getData(week: String, month: String, callback: RWeeklyCallback)

    interface RWeeklyCallback {
        /**
         * Called when data is successfully loaded.
         *
         * @param data Map from day key to `DayData`.
         */
        fun onLoaded(
            data: Map<String, DayData>
        )
        /**
         * Called when an error occurs.
         */
        fun onError(msg: String)
    }

}
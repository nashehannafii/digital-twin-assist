package com.example.digitaltwinpersonalassist.services.network.datasource

import com.example.digitaltwinpersonalassist.services.models.DayData

interface RWeeklyDS {

    fun getData(week: String, month: String, callback: RWeeklyCallback)

    interface RWeeklyCallback {
        fun onLoaded(
            data: Map<String, DayData>
        )
        fun onError(msg: String)
    }

}
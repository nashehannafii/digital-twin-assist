package com.example.digitaltwinpersonalassist.services.network.datasource

interface RDailyDS {

    fun getData(date: String, month: String, callback: RDailyCallback)

    interface RDailyCallback{
        fun onLoaded(msg: String)
        fun onError(msg: String)
    }
}
package com.example.digitaltwinpersonalassist.services.repository

import android.util.Log
import com.example.digitaltwinpersonalassist.services.network.datasource.RDailyDS

class RDailyRepo(private val remoteDS: RDailyDS) : RDailyDS {
    override fun getData(date: String, month: String, callback: RDailyDS.RDailyCallback) {
        remoteDS.getData(date, month, object:RDailyDS.RDailyCallback{
            override fun onLoaded(msg: String) {
                Log.e("SUCCESS REKAP", "onLoaded: " + msg, )
            }

            override fun onError(msg: String) {
                Log.e("ERROR REKAP", "onError: " + msg, )
            }

        })
    }
}
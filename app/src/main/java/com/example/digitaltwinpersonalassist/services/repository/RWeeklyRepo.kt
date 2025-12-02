package com.example.digitaltwinpersonalassist.services.repository

import android.util.Log
import com.example.digitaltwinpersonalassist.services.models.DayData
import com.example.digitaltwinpersonalassist.services.network.datasource.RWeeklyDS

/**
 * Repository untuk rekap mingguan. Mendelegasikan pemanggilan ke remote data
 * source dan meneruskan hasil melalui callback.
 */
class RWeeklyRepo(private val remoteDS: RWeeklyDS) :RWeeklyDS {
    override fun getData(week: String, month: String, callback: RWeeklyDS.RWeeklyCallback) {
        remoteDS.getData(week, month, object: RWeeklyDS.RWeeklyCallback{

            override fun onLoaded(data: Map<String, DayData>) {
                Log.e("SUCCESS WEEKLY", "onLoaded: ", )
                callback.onLoaded(data)
            }

            override fun onError(msg: String) {
                Log.e("ERROR WEEKLY", "onLoaded: " + msg, )
            }

        })
    }
}
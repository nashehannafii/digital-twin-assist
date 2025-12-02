package com.example.digitaltwinpersonalassist.services.repository

import android.util.Log
import com.example.digitaltwinpersonalassist.services.network.datasource.HelloDS

/**
 * Repository yang menjadi lapisan antara data source remote `HelloDS` dan
 * konsumennya. Mendelegasikan pemanggilan ke `remoteDataSource` dan meneruskan
 * hasilnya melalui callback yang diberikan.
 */
class HelloRepo (private val remoteDataSource: HelloDS): HelloDS {
    override fun getData(callback: HelloDS.HelloCallback) {
        remoteDataSource.getData(object : HelloDS.HelloCallback {
            override fun onLoaded(msg: String) {
                Log.e("SUCCESS", "onLoaded: " + msg, )
                callback.onLoaded(msg)
            }

            override fun onError(msg: String) {
                Log.e("ERROR", "onError: " + msg, )
                callback.onError(msg)
            }
        })
    }

}
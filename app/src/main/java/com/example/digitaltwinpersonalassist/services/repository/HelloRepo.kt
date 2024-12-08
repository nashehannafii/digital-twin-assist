package com.example.digitaltwinpersonalassist.services.repository

import android.util.Log
import com.example.digitaltwinpersonalassist.services.network.datasource.HelloDS

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
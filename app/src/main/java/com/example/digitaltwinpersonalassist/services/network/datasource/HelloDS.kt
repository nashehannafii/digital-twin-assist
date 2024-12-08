package com.example.digitaltwinpersonalassist.services.network.datasource

interface HelloDS {

    fun getData(callback: HelloCallback)

    interface HelloCallback{
        fun onLoaded(msg: String)
        fun onError(msg: String)
    }
}
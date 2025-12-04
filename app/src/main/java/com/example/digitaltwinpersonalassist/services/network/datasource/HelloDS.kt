package com.example.digitaltwinpersonalassist.services.network.datasource

/**
 * Abstraction for the "hello" data source.
 * Implementors should invoke the callback when data is available or an error occurs.
 */
interface HelloDS {

    /**
     * Fetch hello data asynchronously and return result via callback.
     */
    fun getData(callback: HelloCallback)

    interface HelloCallback{
        /**
         * Called when data is successfully loaded.
         */
        fun onLoaded(msg: String)
        /**
         * Called when an error occurs.
         */
        fun onError(msg: String)
    }
}
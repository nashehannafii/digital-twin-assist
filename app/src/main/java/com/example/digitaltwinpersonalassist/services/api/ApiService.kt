package com.example.digitaltwinpersonalassist.services.api

import com.example.digitaltwinpersonalassist.services.models.HelloModel
import com.example.digitaltwinpersonalassist.services.models.RDailyModel
import com.example.digitaltwinpersonalassist.services.models.RWeeklyModel
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Declaration of API endpoints used by the application.
 *
 * Methods in this interface are mapped to corresponding HTTP endpoints.
 */
interface ApiService {

        /**
         * Retrieve a simple hello response from the server.
         */
    @GET("/hello")
    fun getData():
            Call<HelloModel>

        /**
         * Retrieve daily recap per hour for a specific date and month.
         *
         * @param date Date string to request.
         * @param month Month string to request.
         */
    @GET("/rekap-harian-perjam")
    fun getRekapDaily(@Query("date") date: String, @Query("month") month: String):
            Call<RDailyModel>

        /**
         * Retrieve weekly recap for a given week and month.
         *
         * @param week Requested week.
         * @param month Requested month.
         */
    @GET("/rekap-harian-perpekan")
    fun getRekap(@Query("week") week: String, @Query("month") month: String):
            Call<RWeeklyModel>
}
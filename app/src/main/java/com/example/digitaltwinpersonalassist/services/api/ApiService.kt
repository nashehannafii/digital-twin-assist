package com.example.digitaltwinpersonalassist.services.api

import com.example.digitaltwinpersonalassist.services.models.HelloModel
import com.example.digitaltwinpersonalassist.services.models.RDailyModel
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/hello")
    fun getData():
            Call<HelloModel>

    @GET("/rekap-harian-perjam")
    fun getRekapDaily(@Query("date") date: String, @Query("month") month: String):
            Call<RDailyModel>

    @GET("/rekap-harian-perpekan")
    fun getRekap(@Query("week") week: String, @Query("month") month: String):
            Call<HelloModel>
}
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
 * Deklarasi endpoint API yang digunakan oleh aplikasi.
 *
 * Metode-metode di interface ini di-map ke endpoint HTTP yang sesuai.
 */
interface ApiService {

    /**
     * Ambil data hello sederhana dari server.
     */
    @GET("/hello")
    fun getData():
            Call<HelloModel>

    /**
     * Ambil rekap harian per jam untuk tanggal dan bulan tertentu.
     *
     * @param date Tanggal (string) yang diminta.
     * @param month Bulan (string) yang diminta.
     */
    @GET("/rekap-harian-perjam")
    fun getRekapDaily(@Query("date") date: String, @Query("month") month: String):
            Call<RDailyModel>

    /**
     * Ambil rekap mingguan untuk pekan dan bulan tertentu.
     *
     * @param week Pekan yang diminta.
     * @param month Bulan yang diminta.
     */
    @GET("/rekap-harian-perpekan")
    fun getRekap(@Query("week") week: String, @Query("month") month: String):
            Call<RWeeklyModel>
}
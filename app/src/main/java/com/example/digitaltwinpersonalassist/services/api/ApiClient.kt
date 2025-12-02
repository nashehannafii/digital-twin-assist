package com.example.digitaltwinpersonalassist.services.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Helper untuk membangun instance `Retrofit` yang terkonfigurasi dengan
 * logging interceptor, converter Gson, dan RxJava call adapter.
 *
 * Gunakan `getCLient()` untuk mendapatkan instance `Retrofit` yang siap pakai.
 */
class ApiClient {

    private var retrofit: Retrofit? = null

    /**
     * Mengembalikan instance `Retrofit` yang dikonfigurasi.
     *
     * @return Retrofit instance (tidak-null).
     */
    fun getCLient() : Retrofit
    {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY;

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        retrofit = Retrofit.Builder().baseUrl("https://dtcockpit.foxecho.id")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit!!
    }
}
package com.example.digitaltwinpersonalassist.services.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Helper to build a configured `Retrofit` instance with a logging interceptor,
 * Gson converter, and RxJava call adapter.
 *
 * Use `getCLient()` to obtain the configured `Retrofit` instance.
 */
class ApiClient {

    private var retrofit: Retrofit? = null

    /**
     * Return the configured `Retrofit` instance.
     *
     * @return Non-null Retrofit instance.
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
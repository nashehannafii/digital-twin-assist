package com.example.digitaltwinpersonalassist.services.network.remote

import android.annotation.SuppressLint
import com.example.digitaltwinpersonalassist.services.api.ApiService
import com.example.digitaltwinpersonalassist.services.models.HelloModel
import com.example.digitaltwinpersonalassist.services.network.datasource.HelloDS
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HelloRDS(private val apiService: ApiService) : HelloDS {
    @SuppressLint("CheckResult")
    override fun getData(callback: HelloDS.HelloCallback) {
        apiService.getData()
            .enqueue(object : Callback<HelloModel> {
                override fun onResponse(
                    call: Call<HelloModel>,
                    response: Response<HelloModel>
                ) {
                    response.body()?.message?.let { callback.onLoaded(it) }
                }

                override fun onFailure(call: Call<HelloModel>, t: Throwable) {
                    callback.onError(t.message.toString())
                }

            })
    }

}
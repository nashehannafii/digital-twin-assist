package com.example.digitaltwinpersonalassist.services.network.remote

import com.example.digitaltwinpersonalassist.services.api.ApiService
import com.example.digitaltwinpersonalassist.services.models.RDailyModel
import com.example.digitaltwinpersonalassist.services.network.datasource.RDailyDS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Remote data source untuk rekap harian per jam.
 */
class RDailyRDS(private val apiService: ApiService) : RDailyDS {
    override fun getData(date: String, month: String, callback: RDailyDS.RDailyCallback) {
        apiService.getRekapDaily(date, month)
            .enqueue(object: Callback<RDailyModel>{
                override fun onResponse(call: Call<RDailyModel>, response: Response<RDailyModel>) {
                    callback.onLoaded(response.body().toString())
                }

                override fun onFailure(call: Call<RDailyModel>, t: Throwable) {
                    callback.onError(t.message.toString())
                }

            })
    }
}
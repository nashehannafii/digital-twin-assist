package com.example.digitaltwinpersonalassist.services.network.remote

import com.example.digitaltwinpersonalassist.services.api.ApiService
import com.example.digitaltwinpersonalassist.services.models.RWeeklyModel
import com.example.digitaltwinpersonalassist.services.network.datasource.RWeeklyDS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RWeeklyRDS(private val apiService: ApiService) : RWeeklyDS {
    override fun getData(week: String, month: String, callback: RWeeklyDS.RWeeklyCallback) {
        apiService.getRekap(week, month)
            .enqueue(object : Callback<RWeeklyModel> {
                override fun onResponse(
                    call: Call<RWeeklyModel>,
                    response: Response<RWeeklyModel>
                ) {
                    response.body().let {
                        callback.onLoaded(it?.data!!)
                    }
                }

                override fun onFailure(call: Call<RWeeklyModel>, t: Throwable) {
                    callback.onError(t.message.toString())
                }
            })
    }

}
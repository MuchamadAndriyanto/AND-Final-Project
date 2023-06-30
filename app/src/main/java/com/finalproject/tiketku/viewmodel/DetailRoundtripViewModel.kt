package com.finalproject.tiketku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.tiketku.model.detailrountrippergi.DetailRoundtripPergiResponse
import com.finalproject.tiketku.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailRoundtripViewModel @Inject constructor(private val api: ApiService) : ViewModel() {

    private val liveDataDetailRoundtrip: MutableLiveData<DetailRoundtripPergiResponse?> = MutableLiveData()

    val livedetailPergi: LiveData<DetailRoundtripPergiResponse?> get() = liveDataDetailRoundtrip

    fun getDetailRoundtrip(id: Int) {
        api.getDetailRountrip(id).enqueue(object : Callback<DetailRoundtripPergiResponse> {
            override fun onResponse(
                call: Call<DetailRoundtripPergiResponse>,
                response: Response<DetailRoundtripPergiResponse>
            ) {
                if (response.isSuccessful) {
                    val detailRoundtrip = response.body()
                    liveDataDetailRoundtrip.postValue(detailRoundtrip)
                } else {
                    liveDataDetailRoundtrip.postValue(null)
                }
            }

            override fun onFailure(call: Call<DetailRoundtripPergiResponse>, t: Throwable) {
                liveDataDetailRoundtrip.postValue(null)
            }
        })
    }

    private val liveDataDetailRoundtripPulang: MutableLiveData<DetailRoundtripPergiResponse?> = MutableLiveData()

    val livedetailPulang: LiveData<DetailRoundtripPergiResponse?> get() = liveDataDetailRoundtripPulang
    fun getDetailRoundtripPulang(id: Int) {
        api.getDetailRountrip(id).enqueue(object : Callback<DetailRoundtripPergiResponse> {
            override fun onResponse(
                call: Call<DetailRoundtripPergiResponse>,
                response: Response<DetailRoundtripPergiResponse>
            ) {
                if (response.isSuccessful) {
                    val detailRoundtrip = response.body()
                    liveDataDetailRoundtripPulang.postValue(detailRoundtrip)
                } else {
                    liveDataDetailRoundtripPulang.postValue(null)
                }
            }

            override fun onFailure(call: Call<DetailRoundtripPergiResponse>, t: Throwable) {
                liveDataDetailRoundtripPulang.postValue(null)
            }
        })
    }
}

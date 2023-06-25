package com.finalproject.tiketku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.tiketku.model.riwayat.Data
import com.finalproject.tiketku.model.riwayat.ResponseRiwayat
import com.finalproject.tiketku.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RiwayatViewModel @Inject constructor(private val api: ApiService) : ViewModel() {

    private val liveDataRiwayat: MutableLiveData<Data?> = MutableLiveData()
    val liveRiwayat: LiveData<Data?> get() = liveDataRiwayat

    fun getRiwayat(token: String) {
        api.getRiwayat(token).enqueue(object : Callback<ResponseRiwayat> {
            override fun onResponse(
                call: Call<ResponseRiwayat>,
                response: Response<ResponseRiwayat>
            ) {
                if (response.isSuccessful) {
                    val responseData = response.body()?.data
                    liveDataRiwayat.value = responseData
                } else {
                    liveDataRiwayat.value = null
                }
            }

            override fun onFailure(call: Call<ResponseRiwayat>, t: Throwable) {
                liveDataRiwayat.value = null
            }
        })
    }
}

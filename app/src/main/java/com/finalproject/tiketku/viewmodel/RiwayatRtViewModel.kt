package com.finalproject.tiketku.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.tiketku.model.riwayat.ResponseRiwayat
import com.finalproject.tiketku.model.riwayatRT.Data
import com.finalproject.tiketku.model.riwayatRT.ResponseRiwayatRT
import com.finalproject.tiketku.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RiwayatRtViewModel @Inject constructor(private val api: ApiService) : ViewModel() {

    val liveDataDetailOrder: MutableLiveData<List<Data>> = MutableLiveData()
    private val orderIdLiveData: MutableLiveData<Int?> = MutableLiveData()
    private val liveDataOrderId: MutableLiveData<Int> = MutableLiveData()

    fun getOrderIdLiveData(): LiveData<Int?> = orderIdLiveData

    fun setOrderIdLiveData(orderId: Int) {
        liveDataOrderId.value = orderId
    }

    // Setter untuk ID
    fun setOrderId(orderId: Int?) {
        orderIdLiveData.value = orderId
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun clearRiwayat() {
        liveDataDetailOrder.value = null
    }

    val liveDataRt: LiveData<List<Data>> get() = liveDataDetailOrder

    private fun getRiwayatByOrderIdRT(orderId: Int) {
        val token = "your_token_here"
        val bearerToken = "Bearer $token"

        val call = api.getRiwayatByOrderIdRT(bearerToken, orderId)

        call.enqueue(object : Callback<ResponseRiwayatRT> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(call: Call<ResponseRiwayatRT>, response: Response<ResponseRiwayatRT>) {
                if (response.isSuccessful) {
                    val rincian = response.body()?.data
                    liveDataDetailOrder.postValue(rincian)
                    Log.d("RiwayatViewModel", "Data riwayat diterima: $rincian")
                } else {
                    Log.e("RiwayatViewModel", "Gagal memperoleh data riwayat")
                    liveDataDetailOrder.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<ResponseRiwayatRT>, t: Throwable) {
                Log.e("RiwayatViewModel", "Gagal memperoleh data riwayat: ${t.message}")
                liveDataDetailOrder.postValue(emptyList())
            }
        })
    }

    fun getRiwayatRt(token: String, orderId: Int?) {
        val bearerToken = "Bearer $token"
        val orderId = liveDataOrderId.value

        val call = if (orderId != null) {
            api.getRiwayatByOrderIdRT(bearerToken, orderId)
        } else {
            api.getRiwayatRt(bearerToken)
        }

        call.enqueue(object : Callback<ResponseRiwayatRT> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(call: Call<ResponseRiwayatRT>, response: Response<ResponseRiwayatRT>) {
                if (response.isSuccessful) {
                    val rincian = response.body()?.data
                    liveDataDetailOrder.postValue(rincian)
                    Log.d("RiwayatViewModel", "Data riwayat diterima: $rincian")
                } else {
                    Log.e("RiwayatViewModel", "Gagal memperoleh data riwayat")
                    liveDataDetailOrder.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<ResponseRiwayatRT>, t: Throwable) {
                Log.e("RiwayatViewModel", "Gagal memperoleh data riwayat: ${t.message}")
                liveDataDetailOrder.postValue(emptyList())
            }
        })
    }
}
package com.finalproject.tiketku.viewmodel

import android.annotation.SuppressLint
import android.util.Log
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

    private val liveDataDetailOrder: MutableLiveData<List<Data>> = MutableLiveData()
    private val orderIdLiveData: MutableLiveData<Int?> = MutableLiveData()

    fun getOrderIdLiveData(): LiveData<Int?> = orderIdLiveData

    // Setter untuk ID
    fun setOrderId(orderId: Int?) {
        orderIdLiveData.value = orderId
    }

    val liveData: LiveData<List<Data>> get() = liveDataDetailOrder

    fun getRiwayat(token: String) {
        val bearerToken = "Bearer $token"

        val call = api.getRiwayat(bearerToken)
        call.enqueue(object : Callback<ResponseRiwayat> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(call: Call<ResponseRiwayat>, response: Response<ResponseRiwayat>) {
                if (response.isSuccessful) {
                    val rincian = response.body()?.data
                    liveDataDetailOrder.postValue(rincian)
                    Log.d("RiwayatViewModel", "Data riwayat diterima: $rincian")
                } else {
                    Log.e("RiwayatViewModel", "Gagal memperoleh data riwayat")
                    liveDataDetailOrder.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<ResponseRiwayat>, t: Throwable) {
                Log.e("RiwayatViewModel", "Gagal memperoleh data riwayat: ${t.message}")
                liveDataDetailOrder.postValue(emptyList())
            }
        })
    }
}

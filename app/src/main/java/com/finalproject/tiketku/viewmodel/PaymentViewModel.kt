package com.finalproject.tiketku.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.tiketku.model.payment.Data
import com.finalproject.tiketku.model.payment.ResponsePayment
import com.finalproject.tiketku.model.riwayat.ResponseRiwayat
import com.finalproject.tiketku.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(private val api: ApiService) : ViewModel() {

    private val liveDataPayment: MutableLiveData<List<Data>> = MutableLiveData()
    private val orderIdLiveData: MutableLiveData<Int?> = MutableLiveData()

    fun postPaymentIdLiveData(): LiveData<Int?> = orderIdLiveData

    val liveData: MutableLiveData<List<Data>> get() = liveDataPayment

    fun postPayment(token: String, paymentData: Data) {
        val bearerToken = "Bearer $token"

        val call = api.postPayment(bearerToken, paymentData)
        call.enqueue(object : Callback<ResponsePayment> {
            override fun onResponse(call: Call<ResponsePayment>, response: Response<ResponsePayment>) {
                if (response.isSuccessful) {
                    val payment = response.body()?.data
                    liveDataPayment.postValue(payment?.let { listOf(it) } ?: emptyList())
                    val orderId = payment?.id_order
                    orderIdLiveData.postValue(orderId)
                    Log.d("PaymentViewModel", "Data pembayaran diterima: $payment")
                } else {
                    Log.e("PaymentViewModel", "Gagal memperoleh data pembayaran")
                    liveDataPayment.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<ResponsePayment>, t: Throwable) {
                Log.e("PaymentViewModel", "Gagal memperoleh data pembayaran: ${t.message}")
                liveDataPayment.postValue(emptyList())
            }
        })
    }
}

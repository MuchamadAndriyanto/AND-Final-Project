package com.finalproject.tiketku.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.tiketku.model.payment.Data
import com.finalproject.tiketku.model.payment.DataPost
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

    private val liveDataPayment: MutableLiveData<DataPost?> = MutableLiveData()

    private val _isPaymentSuccessful = MutableLiveData<Boolean>()

    fun postPayment(token: String, paymentData: DataPost) {
        val bearerToken = "Bearer $token"

        val call = api.postPayment(bearerToken, paymentData)
        call.enqueue(object : Callback<ResponsePayment> {
            override fun onResponse(call: Call<ResponsePayment>, response: Response<ResponsePayment>) {
                if (response.isSuccessful) {
                    val paymentResponse = response.body()
                    val payment = paymentResponse?.data
                    liveDataPayment.postValue(paymentData)
                    _isPaymentSuccessful.postValue(true)
                    Log.d("PaymentViewModel", "Pembayaran sukses: $payment")
                } else {
                    Log.e("PaymentViewModel", "Gagal memproses pembayaran")
                    liveDataPayment.postValue(null)
                    _isPaymentSuccessful.postValue(false)
                }
            }

            override fun onFailure(call: Call<ResponsePayment>, t: Throwable) {
                Log.e("PaymentViewModel", "Gagal memproses pembayaran: ${t.message}")
                liveDataPayment.postValue(null)
                _isPaymentSuccessful.postValue(false)
            }
        })
    }

    fun getPaymentLiveData(): LiveData<DataPost?> {
        return liveDataPayment
    }

    fun getPaymentSuccessfulLiveData(): LiveData<Boolean> {
        return _isPaymentSuccessful
    }
}

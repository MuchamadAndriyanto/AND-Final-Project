package com.finalproject.tiketku.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.tiketku.model.detail.Data
import com.finalproject.tiketku.model.detail.Detail
import com.finalproject.tiketku.model.order.DataOrder
import com.finalproject.tiketku.model.order.ResponseOrder
import com.finalproject.tiketku.model.profile.UpdateProfilePost
import com.finalproject.tiketku.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private var api : ApiService): ViewModel() {

    private val liveDataOrder: MutableLiveData<DataOrder?> = MutableLiveData()

    val liveDetailOrder: MutableLiveData<DataOrder?> get() = liveDataOrder

    fun postOrders(token: String, dataOrder: DataOrder) {
        val bearerToken = "Bearer $token"
        val call = api.postOrderBiodata(bearerToken, dataOrder)

        call.enqueue(object : Callback<ResponseOrder> {
            override fun onResponse(call: Call<ResponseOrder>, response: Response<ResponseOrder>) {
                if (response.isSuccessful) {
                    val detail = response.body()?.data
                    liveDataOrder.postValue(detail)
                } else {
                    liveDataOrder.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseOrder>, t: Throwable) {
                liveDataOrder.postValue(null)
            }
        })
    }
}
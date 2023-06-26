package com.finalproject.tiketku.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.tiketku.model.order.DataOrder
import com.finalproject.tiketku.model.order.ResponseOrder
import com.finalproject.tiketku.model.rincianCO.DataDetailOrder
import com.finalproject.tiketku.model.rincianCO.ResponseRincianOrder
import com.finalproject.tiketku.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private var api : ApiService): ViewModel() {

        private val liveDataOrder: MutableLiveData<DataOrder?> = MutableLiveData()
        private val liveDataDetailOrder: MutableLiveData<DataDetailOrder?> = MutableLiveData()

        val liveDataOrders: MutableLiveData<DataOrder?> get() = liveDataOrder
        val liveDetailOrders: MutableLiveData<DataDetailOrder?> get() = liveDataDetailOrder
        private val orderIdLiveData: MutableLiveData<Int> = MutableLiveData()

        // Getter untuk LiveData ID
        fun getOrderIdLiveData(): LiveData<Int> = orderIdLiveData

        // Setter untuk ID
        fun setOrderId(orderId: Int) {
            orderIdLiveData.value = orderId
        }
        fun getOrderId(): Int? {
            return orderIdLiveData.value
        }

        fun postOrders(token: String, dataOrder: DataOrder) {
            val bearerToken = "Bearer $token"
            val call = api.postOrderBiodata(bearerToken, dataOrder)

            call.enqueue(object : Callback<ResponseOrder> {
                override fun onResponse(call: Call<ResponseOrder>, response: Response<ResponseOrder>) {
                    if (response.isSuccessful) {
                        val detail = response.body()?.data

                        // Menyimpan ID pemesanan
                        detail?.let {
                            val orderId = it.id
                            setOrderId(orderId)
                            Log.d("idOrder","id nya : $orderId")
                            liveDataOrder.postValue(detail)
                        }
                    } else {
                        liveDataOrder.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseOrder>, t: Throwable) {
                    liveDataOrder.postValue(null)
                }
            })
        }

        fun getOrders(token: String, orderId: Int?) {
            val bearerToken = "Bearer $token"
            val call = api.getDetailOrder(bearerToken, orderId)
            call.enqueue(object : Callback<ResponseRincianOrder> {
                override fun onResponse(call: Call<ResponseRincianOrder>, response: Response<ResponseRincianOrder>) {
                    if (response.isSuccessful) {
                        val rincian = response.body()?.data
                        Log.d("idOrder","id get nya : $orderId")
                        liveDataDetailOrder.postValue(rincian)
                    } else {
                        liveDataDetailOrder.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseRincianOrder>, t: Throwable) {
                    liveDataDetailOrder.postValue(null)
                }
            })
        }

        fun getOrderID(): Int {
            return orderIdLiveData.value?.let { it } ?: 0
        }

        fun getKursi(token: String) {
            val bearerToken = "Bearer $token"
            val call = api.getKursi(bearerToken)

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
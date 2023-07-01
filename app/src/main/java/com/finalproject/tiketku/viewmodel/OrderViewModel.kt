package com.finalproject.tiketku.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.tiketku.model.order.DataOrder
import com.finalproject.tiketku.model.order.ResponseOrder
import com.finalproject.tiketku.model.orderRoundTrip.DataOrderRT
import com.finalproject.tiketku.model.orderRoundTrip.ResponseOrderRT
import com.finalproject.tiketku.model.rincianCO.DataDetailOrder
import com.finalproject.tiketku.model.rincianCO.ResponseRincianOrder
import com.finalproject.tiketku.model.rincianCORoundTrip.Data
import com.finalproject.tiketku.model.rincianCORoundTrip.ResponseDetailChekcoutRT
import com.finalproject.tiketku.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private var api : ApiService): ViewModel() {

    private val liveDataGetRT: MutableLiveData<Data?> = MutableLiveData()
    private val liveDataPostRT: MutableLiveData<DataOrderRT?> = MutableLiveData()
    private val liveDataOrder: MutableLiveData<DataOrder?> = MutableLiveData()
    private val liveDataDetailOrder: MutableLiveData<DataDetailOrder?> = MutableLiveData()

    val liveDataOrders: MutableLiveData<DataOrder?> get() = liveDataOrder
    val liveDetailOrders: MutableLiveData<DataDetailOrder?> get() = liveDataDetailOrder
    val liveDataGetRTs: MutableLiveData<Data?> get() = liveDataGetRT
    private val orderIdLiveData: MutableLiveData<Int> = MutableLiveData()
    private val _isOrderIdAvailable = MutableLiveData<Boolean>()
    val isOrderIdAvailable: LiveData<Boolean> = _isOrderIdAvailable

    // Getter untuk LiveData ID
    fun getOrderIdLiveData(): LiveData<Int> = orderIdLiveData

    // Setter untuk ID
    private var orderId: Int? = null
    private var orderIdRT: Int? = null


    fun setOrderId(orderId: Int) {
        this.orderId = orderId
    }
    fun setOrderIdRT(orderIdRT: Int) {
        this.orderIdRT = orderIdRT
    }

    fun getOrderId(): Int? {
        return orderId
    }
    fun getOrderIdRT(): Int? {
        return orderIdRT
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
                        liveDataOrder.postValue(detail)
                        _isOrderIdAvailable.postValue(true)
                    }
                } else {
                    liveDataOrder.postValue(null)
                    _isOrderIdAvailable.postValue(false)
                }
            }

            override fun onFailure(call: Call<ResponseOrder>, t: Throwable) {
                liveDataOrder.postValue(null)
                _isOrderIdAvailable.postValue(false)
            }
        })
    }

    fun getOrders(token: String, orderId: Int?) {
        if (orderId != null) {
            setOrderId(orderId)
        }
        val bearerToken = "Bearer $token"
        val call = api.getDetailOrder(bearerToken, orderId)
        call.enqueue(object : Callback<ResponseRincianOrder> {
            override fun onResponse(
                call: Call<ResponseRincianOrder>,
                response: Response<ResponseRincianOrder>
            ) {
                if (response.isSuccessful) {
                    val rincian = response.body()?.data
                    Log.d("idOrder", "id get nya : ${orderId ?: "null"}")
                    liveDataDetailOrder.postValue(rincian)
                } else {
                    Log.d("SelectSeatCheck", "Gagal mengirim data ke API (token kosong)")
                    liveDataDetailOrder.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseRincianOrder>, t: Throwable) {
                liveDataDetailOrder.postValue(null)
            }
        })
    }

    fun postOrdersRT(token: String, dataOrderRT: DataOrderRT) {
        val bearerToken = "Bearer $token"
        val call = api.postOrderBiodataRT(bearerToken, dataOrderRT)

        call.enqueue(object : Callback<ResponseOrderRT> {
            override fun onResponse(call: Call<ResponseOrderRT>, response: Response<ResponseOrderRT>) {
                if (response.isSuccessful) {
                    val detail = response.body()?.data

                    // Menyimpan ID pemesanan
                    detail?.let {
                        val orderIdRT = it.id
                        setOrderIdRT(orderIdRT)
                        liveDataPostRT.postValue(detail)
                        _isOrderIdAvailable.postValue(true)
                    }
                } else {
                    liveDataPostRT.postValue(null)
                    _isOrderIdAvailable.postValue(false)
                }
            }

            override fun onFailure(call: Call<ResponseOrderRT>, t: Throwable) {
                liveDataPostRT.postValue(null)
                _isOrderIdAvailable.postValue(false)
            }
        })
    }
    fun getOrdersRT(token: String, orderIdRT: Int?) {
        if (orderIdRT != null) {
            setOrderIdRT(orderIdRT)
        }
        val bearerToken = "Bearer $token"
        val call = api.getDetailOrderRT(bearerToken, orderIdRT)
        call.enqueue(object : Callback<ResponseDetailChekcoutRT> {
            override fun onResponse(
                call: Call<ResponseDetailChekcoutRT>,
                response: Response<ResponseDetailChekcoutRT>
            ) {
                if (response.isSuccessful) {
                    val rincian = response.body()?.data
                    Log.d("idOrderRT", "id get nya : ${orderIdRT ?: "null"}")
                    liveDataGetRT.postValue(rincian)
                } else {
                    Log.d("SelectSeatCheck", "Gagal mengirim data ke API (token kosong)")
                    liveDataDetailOrder.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseDetailChekcoutRT>, t: Throwable) {
                liveDataDetailOrder.postValue(null)
            }
        })
    }
}
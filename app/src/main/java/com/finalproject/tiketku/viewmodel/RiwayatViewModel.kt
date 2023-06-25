package com.finalproject.tiketku.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.tiketku.model.notif.DataNotif
import com.finalproject.tiketku.model.notif.NotifResponse
import com.finalproject.tiketku.model.riwayat.Data
import com.finalproject.tiketku.model.riwayat.ResponseRiwayat
import com.finalproject.tiketku.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RiwayatViewModel @Inject constructor(private var api : ApiService): ViewModel() {

    private val liveDataRiwayat: MutableLiveData<List<Data>?> = MutableLiveData()

    val liveRiwayat: MutableLiveData<List<Data>?> get() = liveDataRiwayat

    fun getRiwayat() {
        api.getRiwayat().enqueue(object : Callback<ResponseRiwayat> {
            override fun onResponse(
                call: Call<ResponseRiwayat>,
                response: Response<ResponseRiwayat>
            ) {
                if (response.isSuccessful) {
                    val notifList = response.body()?.data
                    liveDataRiwayat.postValue(notifList)
                } else {
                    liveDataRiwayat.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<ResponseRiwayat>, t: Throwable) {
                liveDataRiwayat.postValue(emptyList())
            }
        })
    }
}
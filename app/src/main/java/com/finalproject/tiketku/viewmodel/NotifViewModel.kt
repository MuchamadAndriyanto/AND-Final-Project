package com.finalproject.tiketku.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.tiketku.model.notif.DataNotif
import com.finalproject.tiketku.model.notif.NotifResponse
import com.finalproject.tiketku.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NotifViewModel  @Inject constructor(private var api : ApiService): ViewModel() {

    private val liveDataNotif: MutableLiveData<List<DataNotif>?> = MutableLiveData()

    val livedataNotifikasi: MutableLiveData<List<DataNotif>?> get() = liveDataNotif

    fun getNotif() {
        api.getNotif().enqueue(object : Callback<NotifResponse> {
            override fun onResponse(
                call: Call<NotifResponse>,
                response: Response<NotifResponse>
            ) {
                if (response.isSuccessful) {
                    val notifList = response.body()?.data
                    liveDataNotif.postValue(notifList)
                } else {
                    liveDataNotif.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<NotifResponse>, t: Throwable) {
                liveDataNotif.postValue(emptyList())
            }
        })
    }
}
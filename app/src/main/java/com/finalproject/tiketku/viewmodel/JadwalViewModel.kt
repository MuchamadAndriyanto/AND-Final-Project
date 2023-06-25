package com.finalproject.tiketku.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.tiketku.model.notif.DataNotif
import com.finalproject.tiketku.model.notif.NotifResponse
import com.finalproject.tiketku.model.rountrip.DataRountip
import com.finalproject.tiketku.model.rountrip.ResponseRountrip
import com.finalproject.tiketku.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class JadwalViewModel @Inject constructor(private var api : ApiService): ViewModel() {

    private val liveDataRountrip: MutableLiveData<List<DataRountip>?> = MutableLiveData()

    val livedataRountrip: MutableLiveData<List<DataRountip>?> get() = liveDataRountrip

    fun getRountrip() {
        api.getRoundtrip().enqueue(object : Callback<ResponseRountrip> {
            override fun onResponse(
                call: Call<ResponseRountrip>,
                response: Response<ResponseRountrip>
            ) {
                if (response.isSuccessful) {
                    val rountripList = response.body()?.data
                    liveDataRountrip.postValue(rountripList)
                } else {
                    liveDataRountrip.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<ResponseRountrip>, t: Throwable) {
                liveDataRountrip.postValue(emptyList())
            }
        })
    }
}
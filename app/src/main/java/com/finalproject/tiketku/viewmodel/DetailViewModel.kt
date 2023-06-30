package com.finalproject.tiketku.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.tiketku.model.detail.Data
import com.finalproject.tiketku.model.detail.Detail
import com.finalproject.tiketku.model.favorit.DataFavorite
import com.finalproject.tiketku.model.favorit.ResponseFavoriteDestination
import com.finalproject.tiketku.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val api: ApiService) : ViewModel() {

    private val liveDetail: MutableLiveData<Data?> = MutableLiveData()

    val liveDetailFavorite: MutableLiveData<Data?> get() = liveDetail

    fun getDetail(id: Int) {
        api.getDetail(id).enqueue(object : Callback<Detail> {
            override fun onResponse(call: Call<Detail>, response: Response<Detail>) {
                if (response.isSuccessful) {
                    val detail = response.body()?.data
                    liveDetail.postValue(detail)
                } else {
                    Log.e("PaymentViewModel", "Gagal menampilkan detail")
                    liveDetail.postValue(null)
                }
            }

            override fun onFailure(call: Call<Detail>, t: Throwable) {
                liveDetail.postValue(null)
            }
        })
    }
}

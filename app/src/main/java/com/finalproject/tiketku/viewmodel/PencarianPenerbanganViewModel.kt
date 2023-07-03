package com.finalproject.tiketku.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.tiketku.model.caripenerbangan.DataCariPenerbangan
import com.finalproject.tiketku.model.caripenerbangan.ResponseCariPenerbangan
import com.finalproject.tiketku.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PencarianPenerbanganViewModel @Inject constructor(private var api : ApiService): ViewModel() {

    private val liveDataCariPenerbangan: MutableLiveData<List<DataCariPenerbangan>?> = MutableLiveData()

    val livedataCariPenerbangan: MutableLiveData<List<DataCariPenerbangan>?> get() = liveDataCariPenerbangan

    fun getFavorite() {
        api.getCariPenerbangan().enqueue(object : Callback<ResponseCariPenerbangan> {
            override fun onResponse(
                call: Call<ResponseCariPenerbangan>,
                response: Response<ResponseCariPenerbangan>
            ) {
                if (response.isSuccessful) {
                    val favoriteList = response.body()?.data
                    liveDataCariPenerbangan.postValue(favoriteList)
                } else {
                    liveDataCariPenerbangan.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<ResponseCariPenerbangan>, t: Throwable) {
                liveDataCariPenerbangan.postValue(emptyList())
            }
        })
    }
}
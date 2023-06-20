package com.finalproject.tiketku.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.tiketku.model.favorit.DataFavorite
import com.finalproject.tiketku.model.favorit.ResponseFavoriteDestination
import com.finalproject.tiketku.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PencarianPenerbanganViewModel @Inject constructor(private var api : ApiService): ViewModel() {

    private val liveDataFavorite: MutableLiveData<List<DataFavorite>?> = MutableLiveData()

    val livedataFavorite: MutableLiveData<List<DataFavorite>?> get() = liveDataFavorite

    fun getFavorite() {
        api.getFavorite().enqueue(object : Callback<ResponseFavoriteDestination> {
            override fun onResponse(
                call: Call<ResponseFavoriteDestination>,
                response: Response<ResponseFavoriteDestination>
            ) {
                if (response.isSuccessful) {
                    val favoriteList = response.body()?.data
                    liveDataFavorite.postValue(favoriteList)
                } else {
                    liveDataFavorite.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<ResponseFavoriteDestination>, t: Throwable) {
                liveDataFavorite.postValue(emptyList())
            }
        })
    }
}
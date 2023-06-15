package com.finalproject.tiketku.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.tiketku.model.BandaraAwal
import com.finalproject.tiketku.model.SearchRespomse
import com.finalproject.tiketku.network.ApiClient
import com.finalproject.tiketku.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinasiViewModel : ViewModel() {


    private val _search = MutableLiveData<List<BandaraAwal>>()
    val search: LiveData<List<BandaraAwal>> = _search

    fun callGetSearch(title: String) {
        ApiClient.instance.getSearch(title).enqueue(object : Callback<SearchRespomse> {
            override fun onResponse(
                call: Call<SearchRespomse>,
                response: Response<SearchRespomse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        _search.postValue(data.data as List<BandaraAwal>?)
                    }
                } else {
                    Log.e("Error : ", "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchRespomse>, t: Throwable) {
                Log.e("Error : ", "onFailure : ${t.message}")
            }

        })
    }
}
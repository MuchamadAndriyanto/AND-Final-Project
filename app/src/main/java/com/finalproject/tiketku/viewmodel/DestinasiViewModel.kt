package com.finalproject.tiketku.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.tiketku.model.search.Data
import com.finalproject.tiketku.model.search.SearchResponse
import com.finalproject.tiketku.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DestinasiViewModel  @Inject constructor(private var api : ApiService): ViewModel() {


    private val _search = MutableLiveData<List<Data>>()
    val search: LiveData<List<Data>> = _search

    fun callGetSearch(kota: String) {
       api.getSearch(kota).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        _search.postValue(data.data as List<Data>?)
                    }
                } else {
                    Log.e("Error : ", "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.e("Error : ", "onFailure : ${t.message}")
            }

        })
    }
}
package com.finalproject.tiketku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.tiketku.model.DataPostUsersItem
import com.finalproject.tiketku.model.ResponseUsersItem
import com.finalproject.tiketku.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersViewModel : ViewModel() {

    private var livedataUser: MutableLiveData<List<DataPostUsersItem>> = MutableLiveData()

    val dataPostUser: LiveData<List<DataPostUsersItem>> get() = livedataUser
    fun postUserRegister(dataUsers: ResponseUsersItem) {
        //memakai callback yang retrofit
       ApiClient.instance.registerUser(dataUsers).enqueue(object : Callback<List<DataPostUsersItem>> {
            override fun onResponse(
                call: Call<List<DataPostUsersItem>>,
                response: Response<List<DataPostUsersItem>>

            ) {
                if (response.isSuccessful) {
                    livedataUser.postValue(response.body())
                } else {
                    livedataUser.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<List<DataPostUsersItem>>, t: Throwable) {
                livedataUser.postValue(emptyList())
            }
        })
    }
}
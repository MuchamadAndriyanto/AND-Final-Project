package com.finalproject.tiketku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.tiketku.model.DataUsers
import com.finalproject.tiketku.model.ResponseUsersItem
import com.finalproject.tiketku.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersViewModel : ViewModel() {

    private val postDataUsers: MutableLiveData<ResponseUsersItem?> = MutableLiveData()

    fun postUsers(): MutableLiveData<ResponseUsersItem?> {
        return postDataUsers
    }

    fun postDataUsers(username: String, email: String, nomor_telepon: String, password: String) {
        val dataUsers = DataUsers(username, email,  nomor_telepon, password)
        ApiClient.instance.postDataUsers(dataUsers).enqueue(object : Callback<ResponseUsersItem> {
            override fun onResponse(
                call: Call<ResponseUsersItem>,
                response: Response<ResponseUsersItem>
            ) {
                if (response.isSuccessful) {
                    postDataUsers.value = response.body()
                } else {
                    postDataUsers.value = null
                }
            }

            override fun onFailure(call: Call<ResponseUsersItem>, t: Throwable) {
                postDataUsers.value = null
            }
        })
    }
}




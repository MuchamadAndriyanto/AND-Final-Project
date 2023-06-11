package com.finalproject.tiketku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.tiketku.model.DataLoginUserItem
import com.finalproject.tiketku.model.DataPostUsersItem
import com.finalproject.tiketku.model.ResponseUsersItem
import com.finalproject.tiketku.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersViewModel : ViewModel() {

    private var livedataUserLogin : MutableLiveData<List<DataLoginUserItem>> = MutableLiveData()
    private var livedataUser: MutableLiveData<List<DataPostUsersItem>> = MutableLiveData()

    val dataLoginUser: LiveData<List<DataLoginUserItem>> get() = livedataUserLogin
    val dataPostUser: LiveData<List<DataPostUsersItem>> get() = livedataUser


    fun postUserRegister(dataUsers: ResponseUsersItem) {

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

    fun postUserLogin(dataUserLogin: DataLoginUserItem){
        //memakai callback yang retrofit
        ApiClient.instance.postLogin(dataUserLogin).enqueue(object : Callback<List<DataLoginUserItem>> {
            override fun onResponse(
                call: Call<List<DataLoginUserItem>>,
                response: Response<List<DataLoginUserItem>>

            ) {
                if (response.isSuccessful){
                    livedataUserLogin.postValue(response.body())
                }else{
                    livedataUserLogin.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<List<DataLoginUserItem>>, t: Throwable) {
                livedataUserLogin.postValue(emptyList())
            }
        })
    }
}
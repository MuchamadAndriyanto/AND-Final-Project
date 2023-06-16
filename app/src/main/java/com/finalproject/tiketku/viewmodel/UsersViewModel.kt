package com.finalproject.tiketku.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.tiketku.model.DataLogin
import com.finalproject.tiketku.model.DataLoginUserItem
import com.finalproject.tiketku.model.DataPassword
import com.finalproject.tiketku.model.DataPostUsersItem
import com.finalproject.tiketku.model.DataResetPassword
import com.finalproject.tiketku.model.DataX
import com.finalproject.tiketku.model.ResponseLogin
import com.finalproject.tiketku.model.ResponseResetPassword
import com.finalproject.tiketku.model.ResponseUsersItem
import com.finalproject.tiketku.model.UpdateProfilePost
import com.finalproject.tiketku.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersViewModel : ViewModel() {

    private var livedataResetPassword: MutableLiveData<ResponseResetPassword?> = MutableLiveData()
    private var livedataPassword: MutableLiveData<DataPassword?> = MutableLiveData()
    private var livedataUserLogin: MutableLiveData<List<DataLogin>?> = MutableLiveData()
    private var livedataUserRegis: MutableLiveData<ResponseUsersItem?> = MutableLiveData()
    private var livedataUpdateProfile : MutableLiveData<List<DataX>?> = MutableLiveData()

    val dataResetPassword: MutableLiveData<ResponseResetPassword?> get() = livedataResetPassword
    val dataLoginPassword: MutableLiveData<DataPassword?> get() = livedataPassword
    val dataLoginUser: MutableLiveData<List<DataLogin>?> get() = livedataUserLogin
    val dataPostUserRegis: MutableLiveData<ResponseUsersItem?> get() = livedataUserRegis
    val dataUpdate: MutableLiveData<List<DataX>?> get() = livedataUpdateProfile

    fun postUserRegister(dataUsers: DataPostUsersItem) {
        ApiClient.instance.registerUser(dataUsers).enqueue(object : Callback<ResponseUsersItem> {
            override fun onResponse(
                call: Call<ResponseUsersItem>,
                response: Response<ResponseUsersItem>
            ) {
                if (response.isSuccessful) {
                    livedataUserRegis.postValue(response.body())
                } else {
                    livedataUserRegis.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseUsersItem>, t: Throwable) {
                livedataUserRegis.postValue(null)
            }
        })
    }

    fun postUserLogin(email: String, password: String) {
        val request = DataLoginUserItem(email, password)

        ApiClient.instance.postLogin(request).enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                val dataLogin = response.body()?.data

                if (response.isSuccessful && dataLogin != null) {
                    livedataUserLogin.postValue(listOf(dataLogin))
                } else {
                    livedataUserLogin.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                livedataUserLogin.postValue(null)
            }
        })
    }

    fun postUserPassword(dataPassword: DataPassword) {
        // Menggunakan callback retrofit
        ApiClient.instance.postPassword(dataPassword).enqueue(object : Callback<DataPassword> {
            override fun onResponse(call: Call<DataPassword>, response: Response<DataPassword>) {
                if (response.isSuccessful) {
                    livedataPassword.postValue(response.body())
                } else {
                    livedataPassword.postValue(null)
                }
            }

            override fun onFailure(call: Call<DataPassword>, t: Throwable) {
                livedataPassword.postValue(null)
            }
        })
    }

    fun postUserResetPassword(dataResetPassword: DataResetPassword) {
        // Menggunakan callback retrofit
        ApiClient.instance.postResetPassword(dataResetPassword)
            .enqueue(object : Callback<ResponseResetPassword> {
                override fun onResponse(
                    call: Call<ResponseResetPassword>,
                    response: Response<ResponseResetPassword>
                ) {
                    if (response.isSuccessful) {
                        livedataResetPassword.postValue(response.body())
                    } else {
                        livedataResetPassword.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseResetPassword>, t: Throwable) {
                    livedataResetPassword.postValue(null)
                }
            })
    }

    fun updateData(token : String, updateprofile: UpdateProfilePost){
        ApiClient.instance.updateProfile("Bearer $token",updateprofile).enqueue(object :
            Callback<List<DataX>>{
            override fun onResponse(
                call: Call<List<DataX>>,
                response: Response<List<DataX>>
            ) {
                if(response.isSuccessful){
                    livedataUpdateProfile.postValue(response.body())
                }else{
                    livedataUpdateProfile.postValue(null)
                }
            }

            override fun onFailure(call: Call<List<DataX>>, t: Throwable) {
                livedataUpdateProfile.postValue(null)
            }

        })
    }
}


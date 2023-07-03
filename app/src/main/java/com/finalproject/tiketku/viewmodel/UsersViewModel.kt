package com.finalproject.tiketku.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.tiketku.model.DataLogin
import com.finalproject.tiketku.model.DataLoginUserItem
import com.finalproject.tiketku.model.DataPassword
import com.finalproject.tiketku.model.DataPostUsersItem
import com.finalproject.tiketku.model.DataResetPassword
import com.finalproject.tiketku.model.ResponseLogin
import com.finalproject.tiketku.model.ResponseResetPassword
import com.finalproject.tiketku.model.ResponseUsersItem
import com.finalproject.tiketku.model.profile.ResponseProfile
import com.finalproject.tiketku.model.profile.UpdateProfilePost
import com.finalproject.tiketku.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private var api : ApiService): ViewModel() {

    private var livedataResetPassword: MutableLiveData<ResponseResetPassword?> = MutableLiveData()
    private var livedataPassword: MutableLiveData<DataPassword?> = MutableLiveData()
    private var livedataUserLogin: MutableLiveData<List<DataLogin>?> = MutableLiveData()
    private var livedataUserRegis: MutableLiveData<ResponseUsersItem?> = MutableLiveData()

    val dataResetPassword: MutableLiveData<ResponseResetPassword?> get() = livedataResetPassword
    val dataLoginPassword: MutableLiveData<DataPassword?> get() = livedataPassword
    val dataLoginUser: MutableLiveData<List<DataLogin>?> get() = livedataUserLogin
    val dataPostUserRegis: MutableLiveData<ResponseUsersItem?> get() = livedataUserRegis

    fun postUserRegister(dataUsers: DataPostUsersItem) {
       api.registerUser(dataUsers).enqueue(object : Callback<ResponseUsersItem> {
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

        api.postLogin(request).enqueue(object : Callback<ResponseLogin> {
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

        api.postPassword(dataPassword).enqueue(object : Callback<DataPassword> {
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

        api.postResetPassword(dataResetPassword)
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

    private val apiService: ApiService = api
    val livedataUpdateProfile = MutableLiveData<ResponseProfile?>()

    fun updateProfile(token: String, updateProfile: UpdateProfilePost) {
        val bearerToken = "Bearer $token"

        val call = apiService.updateProfile(bearerToken, updateProfile)
        call.enqueue(object : Callback<ResponseProfile> {
            override fun onResponse(call: Call<ResponseProfile>, response: Response<ResponseProfile>) {
                if (response.isSuccessful) {
                    livedataUpdateProfile.value = response.body()
                } else {
                    Log.e("UserViewModel", "Failed to update profile")
                    livedataUpdateProfile.value = null
                }
            }

            override fun onFailure(call: Call<ResponseProfile>, t: Throwable) {
                Log.e("UserViewModel", "Failed to update profile", t)
                livedataUpdateProfile.value = null
            }
        })
    }
}
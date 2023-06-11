package com.finalproject.tiketku.network

import com.finalproject.tiketku.model.DataPostUsersItem
import com.finalproject.tiketku.model.ResponseUsersItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("users")
    fun getAllNews(): Call<List<ResponseUsersItem>>

    @POST("register")
    fun registerUser(@Body request: ResponseUsersItem): Call<List<DataPostUsersItem>>


}
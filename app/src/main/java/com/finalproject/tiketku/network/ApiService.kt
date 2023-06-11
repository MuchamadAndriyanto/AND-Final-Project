package com.finalproject.tiketku.network

import com.finalproject.tiketku.model.DataUsers
import com.finalproject.tiketku.model.ResponseUsersItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("users")
    fun getAllNews(): Call<List<ResponseUsersItem>>

    @POST("register")
    fun postDataUsers() : Call<ResponseUsersItem>


}
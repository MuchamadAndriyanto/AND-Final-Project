package com.finalproject.tiketku.model

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class DataPassword(
    @SerializedName("email")
    val email : String
)
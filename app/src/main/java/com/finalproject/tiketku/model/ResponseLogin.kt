package com.finalproject.tiketku.model


import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("data")
    val data: DataLogin,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
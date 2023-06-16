package com.finalproject.tiketku.model


import com.google.gson.annotations.SerializedName

data class ResponseUpdate(
    @SerializedName("data")
    val `data`: DataX,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
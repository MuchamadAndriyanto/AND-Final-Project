package com.finalproject.tiketku.model.profile


import com.google.gson.annotations.SerializedName

data class ResponseProfile(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
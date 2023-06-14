package com.finalproject.tiketku.model


import com.google.gson.annotations.SerializedName

data class ResponseResetPassword(
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
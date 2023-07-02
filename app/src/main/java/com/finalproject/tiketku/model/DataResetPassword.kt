package com.finalproject.tiketku.model

import com.google.gson.annotations.SerializedName

data class DataResetPassword(
    @SerializedName("otp")
    val otp: String,
    @SerializedName("newPassword")
    val newPassword: String


)

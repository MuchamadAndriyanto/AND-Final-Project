package com.finalproject.tiketku.model

import com.google.gson.annotations.SerializedName

data class DataResetPassword(
    @SerializedName("resetToken")
    val resetToken : String,
    @SerializedName("newPassword")
    val newPassword : String
)

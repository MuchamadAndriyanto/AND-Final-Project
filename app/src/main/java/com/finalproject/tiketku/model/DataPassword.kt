package com.finalproject.tiketku.model

import com.google.gson.annotations.SerializedName

data class DataPassword(
    @SerializedName("email")
    val email: String?
)

package com.finalproject.tiketku.model


import com.google.gson.annotations.SerializedName

data class SearchRespomse(
    @SerializedName("data")
    val data: List<BandaraAwal>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
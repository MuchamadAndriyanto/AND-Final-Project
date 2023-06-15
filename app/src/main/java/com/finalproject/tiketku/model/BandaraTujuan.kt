package com.finalproject.tiketku.model


import com.google.gson.annotations.SerializedName

data class BandaraTujuan(
    @SerializedName("kota")
    val kota: String
)
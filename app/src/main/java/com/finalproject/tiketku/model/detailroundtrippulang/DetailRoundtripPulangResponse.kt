package com.finalproject.tiketku.model.detailroundtrippulang


import com.google.gson.annotations.SerializedName

data class DetailRoundtripPulangResponse(
    @SerializedName("data")
    val dataPulang: DataPulang,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
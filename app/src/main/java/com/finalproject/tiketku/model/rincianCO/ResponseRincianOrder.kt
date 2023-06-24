package com.finalproject.tiketku.model.rincianCO


import com.google.gson.annotations.SerializedName

data class ResponseRincianOrder(
    @SerializedName("data")
    val `data`: DataDetailOrder,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)
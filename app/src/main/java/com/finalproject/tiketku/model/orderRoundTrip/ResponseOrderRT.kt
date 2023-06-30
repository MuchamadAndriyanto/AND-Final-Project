package com.finalproject.tiketku.model.orderRoundTrip

import com.google.gson.annotations.SerializedName

data class ResponseOrderRT(
    @SerializedName("data")
    val data: DataOrderRT,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)
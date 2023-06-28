package com.finalproject.tiketku.model.onetrip


import com.google.gson.annotations.SerializedName

data class OneTripResponse(
    @SerializedName("data")
    val `data`: List<DataOneTrip>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
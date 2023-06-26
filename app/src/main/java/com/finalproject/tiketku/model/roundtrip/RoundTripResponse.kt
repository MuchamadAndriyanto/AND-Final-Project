package com.finalproject.tiketku.model.roundtrip


import com.google.gson.annotations.SerializedName

data class RoundTripResponse(
    @SerializedName("data")
    val `data`: List<DataRoundTrip>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
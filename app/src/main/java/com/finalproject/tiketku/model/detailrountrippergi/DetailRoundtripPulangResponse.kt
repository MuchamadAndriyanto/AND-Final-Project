package com.finalproject.tiketku.model.detailrountrippergi


import com.google.gson.annotations.SerializedName

data class DetailRoundtripPulangResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
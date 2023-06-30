package com.finalproject.tiketku.model.detailrountrippergi


import com.google.gson.annotations.SerializedName

data class DetailRoundtripPergiResponse(
    @SerializedName("data")
    val dataPergi: DataPergi,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
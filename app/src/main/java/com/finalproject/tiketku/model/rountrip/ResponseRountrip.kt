package com.finalproject.tiketku.model.rountrip


import com.google.gson.annotations.SerializedName

data class ResponseRountrip(
    @SerializedName("data")
    val `data`: List<DataRountip>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
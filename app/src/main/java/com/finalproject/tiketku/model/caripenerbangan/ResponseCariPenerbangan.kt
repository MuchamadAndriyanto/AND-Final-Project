package com.finalproject.tiketku.model.caripenerbangan


import com.google.gson.annotations.SerializedName

data class ResponseCariPenerbangan(
    @SerializedName("data")
    val `data`: List<DataCariPenerbangan>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
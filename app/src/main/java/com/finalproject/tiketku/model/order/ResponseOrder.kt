package com.finalproject.tiketku.model.order


import com.google.gson.annotations.SerializedName

data class ResponseOrder(
    @SerializedName("data")
    val data: DataOrder,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)
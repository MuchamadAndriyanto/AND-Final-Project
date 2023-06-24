package com.finalproject.tiketku.model.notif


import com.google.gson.annotations.SerializedName

data class NotifResponse(
    @SerializedName("data")
    val `data`: List<DataNotif>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)
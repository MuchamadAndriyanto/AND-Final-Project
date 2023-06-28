package com.finalproject.tiketku.model.onetrip


import com.google.gson.annotations.SerializedName

data class DataOneTrip(
    @SerializedName("hari")
    val hari: String,
    @SerializedName("tanggal_berangkat")
    val tanggalBerangkat: String
)
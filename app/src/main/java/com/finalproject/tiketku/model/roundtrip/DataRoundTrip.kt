package com.finalproject.tiketku.model.roundtrip


import com.google.gson.annotations.SerializedName

data class DataRoundTrip(
    @SerializedName("hari")
    val hari: String,
    @SerializedName("tanggal_berangkat")
    val tanggalBerangkat: String
)
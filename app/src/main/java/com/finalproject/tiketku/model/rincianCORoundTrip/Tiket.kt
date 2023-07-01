package com.finalproject.tiketku.model.rincianCORoundTrip


import com.google.gson.annotations.SerializedName

data class Tiket(
    @SerializedName("berangkat")
    val berangkat: Berangkat,
    @SerializedName("pulang")
    val pulang: Pulang
)
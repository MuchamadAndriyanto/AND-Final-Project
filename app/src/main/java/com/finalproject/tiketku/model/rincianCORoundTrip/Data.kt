package com.finalproject.tiketku.model.rincianCORoundTrip


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("order")
    val order: Order,
    @SerializedName("tiket")
    val tiket: Tiket,
    @SerializedName("totalHargaTiket")
    val totalHargaTiket: String
)
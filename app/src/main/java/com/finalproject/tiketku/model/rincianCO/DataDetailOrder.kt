package com.finalproject.tiketku.model.rincianCO


import com.google.gson.annotations.SerializedName

data class DataDetailOrder(
    @SerializedName("order")
    val order: Order,
    @SerializedName("tiket")
    val tiket: Tiket,
    @SerializedName("totalHargaTiket")
    val totalHargaTiket: String
)
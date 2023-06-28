package com.finalproject.tiketku.model.riwayat

data class Data(
    val order: Order,
    val tiket: Tiket,
    val totalHargaTiket: String,
    val status: Boolean
)
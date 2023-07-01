package com.finalproject.tiketku.model.riwayat

data class Data(
    val order: Order,
    val penerbanganBerangkat: PenerbanganBerangkat,
    val tiketBerangkat: TiketBerangkat,
    val totalHargaTiketBerangkat: String,
    val status: Boolean
)
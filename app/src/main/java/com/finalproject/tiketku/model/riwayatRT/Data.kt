package com.finalproject.tiketku.model.riwayatRT

data class Data(
    val formatTiketKeseluruhan: String,
    val order: Order,
    val penerbanganBerangkat: PenerbanganBerangkat,
    val penerbanganPulang: PenerbanganPulang,
    val tiketBerangkat: TiketBerangkat,
    val tiketPulang: TiketPulang,
    val totalHargaTiketBerangkat: String,
    val totalHargaTiketPulang: String,
    val status: Boolean
)
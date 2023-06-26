package com.finalproject.tiketku.model.riwayat

data class ResponseRiwayat(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)
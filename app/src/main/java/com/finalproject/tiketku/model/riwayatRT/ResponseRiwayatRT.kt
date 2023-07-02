package com.finalproject.tiketku.model.riwayatRT

data class ResponseRiwayatRT(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)
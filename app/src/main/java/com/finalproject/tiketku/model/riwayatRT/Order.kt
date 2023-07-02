package com.finalproject.tiketku.model.riwayatRT

data class Order(
    val email: String,
    val id: Int,
    val id_penerbangan: Int,
    val jumlah_penumpang: Int,
    val kode_booking: String,
    val kursi: String,
    val nama_keluarga: String,
    val nama_lengkap: String,
    val nomor_telepon: String,
    val status_pembayaran: String
)
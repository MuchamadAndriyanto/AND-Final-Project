package com.finalproject.tiketku.model.riwayat

data class Id(
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
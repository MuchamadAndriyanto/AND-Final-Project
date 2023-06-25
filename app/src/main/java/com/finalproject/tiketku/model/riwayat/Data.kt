package com.finalproject.tiketku.model.riwayat

data class Data(
    val email: String,
    val id: Int,
    val id_penerbangan: Int,
    val jumlah_penumpang: Int,
    val kode_booking: String,
    val kursi: String,
    val kursi_terisi: Boolean,
    val nama_keluarga: String,
    val nama_lengkap: String,
    val nomor_telepon: String,
    val status_pembayaran: String,
    val user_id: Int
)
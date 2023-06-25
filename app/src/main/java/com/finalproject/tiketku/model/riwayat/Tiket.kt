package com.finalproject.tiketku.model.riwayat

data class Tiket(
    val bandaraAwal: BandaraAwal,
    val bandaraTujuan: BandaraTujuan,
    val foto: String,
    val id_bandara_asal: String,
    val id_bandara_tujuan: String,
    val id_penerbangan: Int,
    val jam_berangkat: String,
    val jam_kedatangan: String,
    val maskapai: Maskapai,
    val selisih_jam: Int,
    val selisih_menit: Int,
    val tanggal_berangkat: String,
    val tanggal_kedatangan: String
)
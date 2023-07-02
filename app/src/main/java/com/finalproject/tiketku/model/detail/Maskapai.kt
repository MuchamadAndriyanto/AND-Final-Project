package com.finalproject.tiketku.model.detail

import com.google.gson.annotations.SerializedName

data class Maskapai(
    val harga_tiket: String,
    val id_maskapai: Int,
    val kode_maskapai: String,
    val nama_maskapai: String
)
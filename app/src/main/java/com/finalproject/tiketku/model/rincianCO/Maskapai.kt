package com.finalproject.tiketku.model.rincianCO


import com.google.gson.annotations.SerializedName

data class Maskapai(
    @SerializedName("id_maskapai")
    val idMaskapai: Int,
    @SerializedName("kode_maskapai")
    val kode_maskapai: String,
    @SerializedName("nama_maskapai")
    val namaMaskapai: String,
    @SerializedName("harga_tiket")
    val hargaTiket: String
)
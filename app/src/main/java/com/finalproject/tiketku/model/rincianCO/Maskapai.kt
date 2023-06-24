package com.finalproject.tiketku.model.rincianCO


import com.google.gson.annotations.SerializedName

data class Maskapai(
    @SerializedName("harga_tiket")
    val hargaTiket: String,
    @SerializedName("id_maskapai")
    val idMaskapai: Int,
    @SerializedName("nama_maskapai")
    val namaMaskapai: String
)
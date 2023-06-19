package com.finalproject.tiketku.model.favorit


import com.google.gson.annotations.SerializedName

data class Maskapai(
    @SerializedName("harga_tiket")
    val hargaTiket: String,
    @SerializedName("nama_maskapai")
    val namaMaskapai: String
)
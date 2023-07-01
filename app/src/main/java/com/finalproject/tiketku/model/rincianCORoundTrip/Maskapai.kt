package com.finalproject.tiketku.model.rincianCORoundTrip


import com.google.gson.annotations.SerializedName

data class Maskapai(
    @SerializedName("harga_tiket")
    val hargaTiket: String,
    @SerializedName("kode_maskapai")
    val kodeMaskapai: String,
    @SerializedName("nama_maskapai")
    val namaMaskapai: String
)
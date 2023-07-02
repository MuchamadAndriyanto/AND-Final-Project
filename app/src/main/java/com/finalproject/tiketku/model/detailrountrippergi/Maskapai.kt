package com.finalproject.tiketku.model.detailrountrippergi


import com.google.gson.annotations.SerializedName

data class Maskapai(
    @SerializedName("harga_tiket")
    val hargaTiket: String,
    @SerializedName("kode_maskapai")
    val kode_maskapai: String,
    @SerializedName("id_maskapai")
    val idMaskapai: Int,
    @SerializedName("nama_maskapai")
    val namaMaskapai: String
)
package com.finalproject.tiketku.model.detailroundtrippulang


import com.google.gson.annotations.SerializedName

data class Maskapai(
    @SerializedName("harga_tiket")
    val hargaTiket: String,
    @SerializedName("id_maskapai")
    val idMaskapai: Int,
    @SerializedName("kode_maskapai")
    val kodeMaskapai: String,
    @SerializedName("nama_maskapai")
    val namaMaskapai: String
)
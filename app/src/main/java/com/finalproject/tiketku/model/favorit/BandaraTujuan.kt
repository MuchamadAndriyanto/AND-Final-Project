package com.finalproject.tiketku.model.favorit


import com.google.gson.annotations.SerializedName

data class BandaraTujuan(
    @SerializedName("kota")
    val kota: String,
    @SerializedName("nama_bandara")
    val namaBandara: String,
    @SerializedName("negara")
    val negara: String
)
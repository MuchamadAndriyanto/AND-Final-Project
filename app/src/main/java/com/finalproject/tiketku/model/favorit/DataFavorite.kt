package com.finalproject.tiketku.model.favorit


import com.google.gson.annotations.SerializedName

data class DataFavorite(
    @SerializedName("bandaraAwal")
    val bandaraAwal: BandaraAwal,
    @SerializedName("bandaraTujuan")
    val bandaraTujuan: BandaraTujuan,
    @SerializedName("foto")
    val foto: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("id_bandara_asal")
    val idBandaraAsal: String,
    @SerializedName("id_bandara_tujuan")
    val idBandaraTujuan: String,
    @SerializedName("maskapai")
    val maskapai: Maskapai,
    @SerializedName("tanggal_berangkat")
    val tanggalBerangkat: String
)
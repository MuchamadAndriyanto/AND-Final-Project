package com.finalproject.tiketku.model.caripenerbangan


import com.google.gson.annotations.SerializedName

data class DataCariPenerbangan(
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
    @SerializedName("jam_berangkat")
    val jamBerangkat: String,
    @SerializedName("jam_kedatangan")
    val jamKedatangan: String,
    @SerializedName("maskapai")
    val maskapai: Maskapai,
    @SerializedName("selisih_jam")
    val selisihJam: Int,
    @SerializedName("selisih_menit")
    val selisihMenit: Int,
    @SerializedName("tanggal_berangkat")
    val tanggalBerangkat: String,
    @SerializedName("hari")
    val hari: String
)
package com.finalproject.tiketku.model.detailrountrippergi


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("bandaraAwal")
    val bandaraAwal: BandaraAwal,
    @SerializedName("bandaraTujuan")
    val bandaraTujuan: BandaraTujuan,
    @SerializedName("foto")
    val foto: String,
    @SerializedName("id_bandara_asal")
    val idBandaraAsal: String,
    @SerializedName("id_bandara_tujuan")
    val idBandaraTujuan: String,
    @SerializedName("id_penerbangan")
    val idPenerbangan: Int,
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
    @SerializedName("tanggal_kedatangan")
    val tanggalKedatangan: String
)
package com.finalproject.tiketku.model.orderRoundTrip


import com.google.gson.annotations.SerializedName

data class DataOrderRT(
    @SerializedName("id")
    val id: Int,
    @SerializedName("id_penerbangan_berangkat")
    val id_penerbangan_berangkat: Int,
    @SerializedName("id_penerbangan_pulang")
    val id_penerbangan_pulang: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("jumlah_penumpang")
    val jumlahPenumpang: Int,
    @SerializedName("kursi")
    val kursi: String,
    @SerializedName("nama_keluarga")
    val namaKeluarga: String,
    @SerializedName("nama_lengkap")
    val namaLengkap: String,
    @SerializedName("nomor_telepon")
    val nomorTelepon: String
)
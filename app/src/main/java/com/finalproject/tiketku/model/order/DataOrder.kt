package com.finalproject.tiketku.model.order


import com.google.gson.annotations.SerializedName

data class DataOrder(
    @SerializedName("id")
    val id: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("id_penerbangan")
    val idPenerbangan: Int,
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
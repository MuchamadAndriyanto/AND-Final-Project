package com.finalproject.tiketku.model.rincianCORoundTrip


import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("id_penerbangan")
    val idPenerbangan: Int,
    @SerializedName("id_penerbangan_pulang")
    val idPenerbanganPulang: Int,
    @SerializedName("jumlah_penumpang")
    val jumlahPenumpang: Int,
    @SerializedName("kode_booking")
    val kodeBooking: String,
    @SerializedName("kursi")
    val kursi: String,
    @SerializedName("nama_keluarga")
    val namaKeluarga: String,
    @SerializedName("nama_lengkap")
    val namaLengkap: String,
    @SerializedName("nomor_telepon")
    val nomorTelepon: String
)
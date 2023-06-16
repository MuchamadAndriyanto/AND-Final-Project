package com.finalproject.tiketku.model


import com.google.gson.annotations.SerializedName

data class DataX(
    @SerializedName("alamat")
    val alamat: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("nama_lengkap")
    val namaLengkap: String,
    @SerializedName("nomor_telepon")
    val nomorTelepon: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String
)
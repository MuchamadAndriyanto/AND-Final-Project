package com.finalproject.tiketku.model


import com.google.gson.annotations.SerializedName

data class ResponseUsersItem(
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
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)
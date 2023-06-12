package com.finalproject.tiketku.model


import com.google.gson.annotations.SerializedName

data class DataLogin(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("accessTokenExpiresIn")
    val accessTokenExpiresIn: String,
    @SerializedName("alamat")
    val alamat: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("nama_lengkap")
    val namaLengkap: String,
    @SerializedName("nomor_telepon")
    val nomorTelepon: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("refreshTokenExpiresIn")
    val refreshTokenExpiresIn: String,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)
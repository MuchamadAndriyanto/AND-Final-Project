package com.finalproject.tiketku.model.profile

import com.google.gson.annotations.SerializedName

data class UpdateProfilePost(

    @SerializedName("username")
    val username: String,
    @SerializedName("nama_lengkap")
    val namaLengkap: String,
    @SerializedName("alamat")
    val alamat: String,
    @SerializedName("nomor_telepon")
    val nomorTelepon: String
)

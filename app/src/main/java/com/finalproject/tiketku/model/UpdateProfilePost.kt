package com.finalproject.tiketku.model

import com.google.gson.annotations.SerializedName

data class UpdateProfilePost(
    @SerializedName("username")
    val username: String,
    @SerializedName("namaLengkap")
    val namaLengkap: String,
    @SerializedName("alamat")
    val alamat: String
)

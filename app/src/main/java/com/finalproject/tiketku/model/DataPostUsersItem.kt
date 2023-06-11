package com.finalproject.tiketku.model

import com.google.gson.annotations.SerializedName

data class DataPostUsersItem(

    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("nomor_telepon")
    val nomor_telepon: String,
    @SerializedName("password")
    val password: String

)

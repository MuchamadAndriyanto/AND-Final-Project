package com.finalproject.tiketku.model.notif


import com.google.gson.annotations.SerializedName

data class DataNotif(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("jam")
    val jam: String,
    @SerializedName("judul")
    val judul: String,
    @SerializedName("pesan")
    val pesan: String,
    @SerializedName("pesanTambahan")
    val pesanTambahan: String,
    @SerializedName("tanggal")
    val tanggal: String,
    @SerializedName("isRead")
    var isRead: Boolean
)
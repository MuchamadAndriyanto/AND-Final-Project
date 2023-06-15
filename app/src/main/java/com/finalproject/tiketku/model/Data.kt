package com.finalproject.tiketku.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("bandaraAwal")
    val bandaraAwal: BandaraAwal,
    @SerializedName("bandaraTujuan")
    val bandaraTujuan: BandaraTujuan
)
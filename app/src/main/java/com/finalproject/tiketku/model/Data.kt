package com.finalproject.tiketku.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("bandaraAwal")
    val bandaraAwal: List<BandaraAwal>,
    @SerializedName("bandaraTujuan")
    val bandaraTujuan: BandaraTujuan
)
package com.finalproject.tiketku.model.favorit


import com.google.gson.annotations.SerializedName

data class ResponseFavoriteDestination(
    @SerializedName("data")
    val `data`: List<DataFavorite>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
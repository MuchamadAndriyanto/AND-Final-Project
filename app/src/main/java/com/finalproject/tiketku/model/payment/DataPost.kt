package com.finalproject.tiketku.model.payment

data class DataPost(
    val id: Int,
    val cardNumber: String,
    val cardName: String,
    val cvv: String,
    val expiryDate: String

)

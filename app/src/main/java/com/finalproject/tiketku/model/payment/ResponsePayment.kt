package com.finalproject.tiketku.model.payment

data class ResponsePayment(
    val `data`: Data,
    val message: String,
    val success: Boolean
)
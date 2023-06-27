package com.finalproject.tiketku.model.payment

data class Data(
    val card_name: String,
    val card_number: String,
    val cvv: String,
    val expiry_date: String,
    val id: Int,
    val id_order: Int,
    val user_id: Int
)
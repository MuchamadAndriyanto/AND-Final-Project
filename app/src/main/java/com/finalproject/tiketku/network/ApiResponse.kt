package com.finalproject.tiketku.network

data class ApiResponse(
    val success: Boolean,
    val message: String?,
    val data: Any?
)

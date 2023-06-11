package com.finalproject.tiketku.model

interface PassengerCountListener {
    fun onPassengerCountUpdated(baby: Int, child: Int, adult: Int)
}

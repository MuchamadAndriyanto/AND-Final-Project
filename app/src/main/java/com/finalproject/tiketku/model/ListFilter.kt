package com.finalproject.tiketku.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListFilter(var text1 : String, var text2 : String)  : Parcelable
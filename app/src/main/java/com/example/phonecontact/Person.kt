package com.example.phonecontact

import android.graphics.Bitmap

data class Person(
    val name: String,
    val phoneNumber: String,
    var img: Bitmap?
)

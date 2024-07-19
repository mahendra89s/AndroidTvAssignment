package com.example.androidtvapp.utils

import java.text.SimpleDateFormat
import java.util.Date

fun String.dateTimeFormatter() : String {
    val inputFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val outputFormat: SimpleDateFormat = SimpleDateFormat("dd MMM yyyy hh:mm a")
    val parsedDate: Date? = inputFormat.parse(this)
    return parsedDate?.let { outputFormat.format(it) } ?: ""
}
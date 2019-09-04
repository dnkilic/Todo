package com.dnkilic.todo.data

import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT = "dd.MM.yyyy"

fun dateToLong(date: String): Long {
    return SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).parse(date)!!.time
}

fun dateToString(date: Long): String {
    val formatter = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = date
    return formatter.format(calendar.time)
}
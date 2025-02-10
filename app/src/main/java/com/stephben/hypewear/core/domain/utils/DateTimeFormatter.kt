package com.stephben.hypewear.core.domain.utils


import com.google.firebase.firestore.FieldValue
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getCurrentTimeAsTimestamp(): FieldValue = FieldValue.serverTimestamp()


fun convertDateFormat(date: Date?): String {
    if (date == null) {
        return  ""
    }
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return  dateFormat.format(date)
}
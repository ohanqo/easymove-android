package com.easymove.easymove.shared.utils

import java.text.ParseException
import java.text.SimpleDateFormat

object TimeUtils {
    fun formatDay(inputDate: String): String {
        return try {
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(inputDate)
            SimpleDateFormat("dd MMM yyyy").format(date)
        } catch (e: ParseException) {
            inputDate
        }
    }

    fun formatHour(inputDate: String): String {
        return try {
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(inputDate)
            return SimpleDateFormat("HH'h'mm").format(date)
        } catch (e: ParseException) {
            inputDate
        }
    }
}
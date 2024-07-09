package com.nagane.table.ui.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.abs

fun getYear(dateTimeString: String) : String {
    return dateTimeString.substring(0, 4)
}

fun getMonth(dateTimeString: String) : String {
    return dateTimeString.substring(5, 7)
}

fun getDay(dateTimeString: String) : String {
    return dateTimeString.substring(8, 10)
}

fun getHour(dateTimeString: String) : String {
    return dateTimeString.substring(11, 13)
}

fun getMinute(dateTimeString: String) : String {
    return dateTimeString.substring(14, 16)
}

fun getSecond(dateTimeString: String) : String {
    return dateTimeString.substring(17, 19)
}

fun getDate(dateTimeString: String) : String {
    return dateTimeString.substring(0, 10)
}

fun getTime(dateTimeString: String) : String {
    return dateTimeString.substring(11, 19)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getTimeDifferenceString(dateTimeString: String) : String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    val dateTime = LocalDateTime.parse(dateTimeString.subSequence(0, 19), formatter)

    val nowTime = LocalDateTime.now()

    val minutesDiff = ChronoUnit.MINUTES.between(dateTime, nowTime)
    val hoursDiff = ChronoUnit.HOURS.between(dateTime, nowTime)
    val daysDiff = ChronoUnit.DAYS.between(dateTime, nowTime)

    return when {
        abs(minutesDiff) < 60 -> "${abs(minutesDiff)} 분 전"
        abs(hoursDiff) < 24 -> "${abs(hoursDiff)} 시간 전"
        else -> "${abs(daysDiff)} 일 전"
    }

}
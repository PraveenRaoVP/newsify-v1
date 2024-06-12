package android.example.newsapp.utils

import android.util.Log
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateFormatUtil {
    companion object {
        fun calculateFormattedDateTime(date: String, time: String): String {
            val dateTimeRegex = Regex("""(\w+), (\d+) (\w+), (\d+). Time: (\d+):(\d+) (\w+)""") //
            val matchResult = dateTimeRegex.find("$date. Time: $time") ?: return ""

            val (_, day, month, year, hour, minute, amPm) = matchResult.destructured

            // convert month to number
            val monthMap = mapOf(
                "January" to "01",
                "February" to "02",
                "March" to "03",
                "April" to "04",
                "May" to "05",
                "June" to "06",
                "July" to "07",
                "August" to "08",
                "September" to "09",
                "October" to "10",
                "November" to "11",
                "December" to "12"
            )

            val numMonth = monthMap[month] ?: return ""

            val formattedHour = if (amPm.lowercase() == "pm") {
                (hour.toInt() + 12).toString()
            } else {
                hour
            }
            Log.i("DateFormatUtil","$year-$numMonth-$day $formattedHour:$minute:00")
            return "$year-$numMonth-$day $formattedHour:$minute:00"
        }

        fun getCurrentDate(): String {
            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            return currentDateTime.format(formatter)
        }
    }
}
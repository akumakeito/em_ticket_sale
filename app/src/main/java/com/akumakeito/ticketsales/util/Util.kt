package com.akumakeito.ticketsales.util

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.akumakeito.ticketsales.R
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun createToast(context: Context, message: String) {
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT
    ).show()
}

fun hideKeyboard(view: TextView, context: Context) {
    val imm = ContextCompat.getSystemService(context, InputMethodManager::class.java)
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun dayShortMonthDayFormatDate(dateInMilliseconds: Long): String {
    val dateFormat = SimpleDateFormat("dd MMM, E", Locale("ru"))
    return dateFormat.format(dateInMilliseconds)
}

fun getDatePicker(selectingDate: String, fromDate: Long): MaterialDatePicker<Long> {

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = fromDate
    calendar.get(Calendar.MONTH)
    val month = calendar.timeInMillis
    val constraintsBuilder = CalendarConstraints.Builder()
        .setOpenAt(month)
        .setFirstDayOfWeek(Calendar.MONDAY)
        .setValidator(DateValidatorPointForward.from(fromDate))

    return MaterialDatePicker.Builder.datePicker()
        .setTheme(R.style.ThemeOverlay_App_DatePicker)
        .setTitleText(selectingDate)
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .setCalendarConstraints(constraintsBuilder.build())
        .build()
}

fun getDatePicker(selectingDate: String): MaterialDatePicker<Long> {

    val constraintsBuilder = CalendarConstraints.Builder()
        .setFirstDayOfWeek(Calendar.MONDAY)
        .setValidator(DateValidatorPointForward.now())

    return MaterialDatePicker.Builder.datePicker()
        .setTheme(R.style.ThemeOverlay_App_DatePicker)
        .setTitleText(selectingDate)
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .setCalendarConstraints(constraintsBuilder.build())
        .build()
}

fun formatTicketTime(dateTime: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val outputFormat = SimpleDateFormat("HH:mm")

    val date = inputFormat.parse(dateTime)
    return date?.let { outputFormat.format(it) } ?: dateTime

}

fun getTravelTime(departure: String, arrival: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val departureDate = dateFormat.parse(departure)
    val arrivalDate = dateFormat.parse(arrival)
    val durationInMillis = arrivalDate.time - departureDate.time
    val durationInMinutes = durationInMillis / (1000 * 60)
    var hours = durationInMinutes / 60
    val minutes = durationInMinutes % 60


    val travelMinutes = when (minutes) {
        in 0..20 -> ""
        in 21..40 -> ".5"
        else -> {
            hours++
            ""
        }
    }

    val travelHours = when (hours) {
        0L -> ""
        in 1 until 24 -> "$hours"
        else -> {
            if (hours % 24 == 0L) {
                "${hours / 24}д"
            } else {
                "${hours / 24}д ${hours % 24}ч"
            }
        }
    }

    return travelHours + travelMinutes
}

fun dayFullMonthDateFormat(departureDate: Long): String {
    val dateFormat = SimpleDateFormat("dd MMMM", Locale("ru"))
    val date = Date(departureDate)
    return dateFormat.format(date)
}



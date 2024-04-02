package com.ssafy.d101.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

data class CalendarModel(
    val selectedDate: Date,
    val visibleDates: List<Date>
) {
    val startDate: Date = visibleDates.first()
    val endDate: Date = visibleDates.last()

    data class Date(
        val date: LocalDate,
        val isSelected: Boolean,
        val isToday: Boolean
    ) {
        val day: String = date.format(DateTimeFormatter.ofPattern("E", Locale.KOREA))
    }
}
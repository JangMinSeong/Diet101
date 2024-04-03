package com.ssafy.d101.viewmodel

import androidx.lifecycle.ViewModel
import com.ssafy.d101.model.CalendarModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.Stream
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor() : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate = _selectedDate.asStateFlow()

    private val _calendarModel = MutableStateFlow<CalendarModel?>(null)
    val calendarModel = _calendarModel.asStateFlow()

    init {
        updateSelectedDate(_selectedDate.value)
    }

    fun updateSelectedDate(selectedDate: LocalDate) {
        _selectedDate.value = selectedDate
        updateCalendarModel(selectedDate)
    }

    private fun updateCalendarModel(selectedDate: LocalDate) {
        val firstDayOfWeek = selectedDate.with(DayOfWeek.MONDAY)
        val lastDayOfWeek = firstDayOfWeek.plusDays(6) // 한 주의 마지막 날
        val visibleDates = getDatesBetween(firstDayOfWeek, lastDayOfWeek)
        _calendarModel.value = toUiModel(visibleDates, selectedDate)
//        _selectedDate.value = date
//        loadCalendarData(startDate = _today.value, lastSelectedDate = date)
    }

//    fun getData(startDate: LocalDate = _today.value, lastSelectedDate: LocalDate = _selectedDate.value ?: LocalDate.now()): CalendarModel {
//        val firstDayOfWeek = startDate.with(DayOfWeek.MONDAY)
//        val endDayOfWeek = firstDayOfWeek.plusDays(7)
//        val visibleDates = getDatesBetween(firstDayOfWeek, endDayOfWeek)
//        return toUiModel(visibleDates, lastSelectedDate)
//    }

//    private fun loadCalendarData(startDate: LocalDate = _today.value, lastSelectedDate: LocalDate = _selectedDate.value ?: LocalDate.now()) {
//        val firstDayOfWeek = startDate.with(DayOfWeek.MONDAY)
//        val endDayOfWeek = firstDayOfWeek.plusDays(6) // 주의 마지막 날을 포함시키기 위해 7이 아닌 6을 더합니다.
//        val visibleDates = getDatesBetween(firstDayOfWeek, endDayOfWeek)
//        val newCalendarModel = toUiModel(visibleDates, lastSelectedDate)
//        _calendarModel.value = newCalendarModel
//    }

    private fun getDatesBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val numOfDays = ChronoUnit.DAYS.between(startDate, endDate) + 1
        return Stream.iterate(startDate) { date ->
            date.plusDays(/* daysToAdd = */ 1)
        }
            .limit(numOfDays)
            .collect(Collectors.toList())
    }

    private fun toUiModel(
        dateList: List<LocalDate>,
        lastSelectedDate: LocalDate
    ): CalendarModel {
        return CalendarModel(
            selectedDate = toItemUiModel(lastSelectedDate, true),
            visibleDates = dateList.map {
                toItemUiModel(it, it.isEqual(lastSelectedDate))
            },
        )
    }

    private fun toItemUiModel(date: LocalDate, isSelectedDate: Boolean) = CalendarModel.Date(
        isSelected = isSelectedDate,
        isToday = date.isEqual(LocalDate.now()),
        date = date,
    )
}
package com.ssafy.d101.ui.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.d101.model.CalendarModel
import com.ssafy.d101.ui.theme.Ivory
import com.ssafy.d101.viewmodel.CalendarViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun Header(
    data: CalendarModel,
    onPrevClickListener: (LocalDate) -> Unit,
    onNextClickListener: (LocalDate) -> Unit,
    calendarViewModel: CalendarViewModel
    ) {
    Row(
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        Text(
            text = if (data.selectedDate.isToday) " Today" else data.selectedDate.date.format(
                DateTimeFormatter.ofPattern("yyyy년 MM월", Locale.KOREA)
            ),
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        IconButton(onClick = { onPrevClickListener(data.startDate.date)}) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Previous" )
        }
        IconButton(onClick = { onNextClickListener(data.endDate.date)}) {
            Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "Next" )
        }
    }
}

@Composable
fun CalendarApp(modifier: Modifier = Modifier, calendarViewModel: CalendarViewModel) {
    val calendarModel by calendarViewModel.calendarModel.collectAsState()

    calendarModel?.let { model ->
        Column(modifier = modifier) {
            Header(
                data = model,
                onPrevClickListener = {
                    calendarViewModel.updateSelectedDate(it.minusWeeks(1))
                },
                onNextClickListener = {
                    calendarViewModel.updateSelectedDate(it.plusWeeks(1))
                },
                calendarViewModel = calendarViewModel
            )
            Content(data = model) { date ->
                // 날짜 선택 시 ViewModel의 선택된 날짜를 업데이트합니다.
                calendarViewModel.updateSelectedDate(date.date)
            }
        }
    }

//    Column() {
//        Header(
//            data = calendarUiModel,
//            onPrevClickListener = { startDate ->
//                val finalStartDate = startDate.minusDays(1)
//                calendarUiModel = dataSource.getData(
//                    startDate = finalStartDate,
//                    lastSelectedDate = calendarUiModel.selectedDate.date
//                )
//            },
//            onNextClickListener = { endDate ->
//                val finalEndDate = endDate.plusDays(2)
//                calendarUiModel = dataSource.getData(
//                    startDate = finalEndDate,
//                    lastSelectedDate = calendarUiModel.selectedDate.date
//                )
//            }
//        )
//        Content(data = calendarUiModel, onDateClickListener = { date ->
//            // refresh the CalendarUiModel with new data
//            // by changing only the `selectedDate` with the date selected by User
//            calendarUiModel = calendarUiModel.copy(
//                selectedDate = date,
//                visibleDates = calendarUiModel.visibleDates.map {
//                    it.copy(
//                        isSelected = it.date.isEqual(date.date)
//                    )
//                }
//            )
//        })
//    }
}

@Composable
fun ContentItem(
    date: CalendarModel.Date,
    onDateClickListener: (CalendarModel.Date) -> Unit
){
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .clickable { onDateClickListener(date) },
        colors = CardDefaults.cardColors(
            containerColor = if (date.isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                Ivory
            }
        ),
    ) {
        Column(
            modifier = Modifier
                .width(40.dp)
                .height(48.dp)
                .padding(4.dp)
        ) {
            Text(text = date.day, modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodySmall)
            Text(text = date.date.dayOfMonth.toString(), modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun Content(
    data: CalendarModel,
    onDateClickListener: (CalendarModel.Date) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        // LazyRow를 Box 내에서 가운데 정렬합니다.
        LazyRow(
            modifier = Modifier.align(Alignment.Center)
        ) {
            items(items = data.visibleDates) { date ->
                ContentItem(date = date, onDateClickListener = onDateClickListener)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CalendarAppPreview() {
    CalendarApp(modifier = Modifier.padding(16.dp), calendarViewModel = CalendarViewModel())
}
package com.example.myapplication.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.myapplication.R
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.YearMonth
import java.util.*

@Composable
fun CalendarScreen() {
    // 獲取當天的日期
    val today = remember { LocalDate.now() }

    // 狀態管理：目前顯示的月份 (改為獲取當前系統月份)
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }

    // 狀態管理：目前選中的日期 (初始化為今天)
    var selectedDate by remember { mutableStateOf(today) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.main_color_normal))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // 日曆主體卡片
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 1. 標頭：切換月份
                CalendarHeader(
                    currentMonth = currentMonth,
                    onMonthChange = { currentMonth = it }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 2. 星期列 (日、一、二...)
                DaysOfWeekRow()

                Spacer(modifier = Modifier.height(16.dp))

                // 3. 日期網格
                CalendarGrid(
                    currentMonth = currentMonth,
                    selectedDate = selectedDate,
                    onDateSelected = { date ->
                        selectedDate = date
                        // --- 接口事件點 ---
                        // 在這裡處理點擊日期後的邏輯，例如：
                        // println("使用者選中了：$date")
                        // 或是更新 ViewModel 中的 ID 來查詢當天資料
                    }
                )
            }
        }

        // 未來下方清單放置處 (Column/LazyColumn)
    }
}

@Composable
fun CalendarHeader(
    currentMonth: YearMonth,
    onMonthChange: (YearMonth) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onMonthChange(currentMonth.minusMonths(1)) }) {
            Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Prev", tint = colorResource(R.color.main_color_dark), modifier = Modifier.size(32.dp))
        }

        Text(
            text = "${currentMonth.year} 年 ${currentMonth.monthValue} 月",
            fontSize = 22.sp,
            fontWeight = Bold,
            color = colorResource(R.color.main_color_dark)
        )

        IconButton(onClick = { onMonthChange(currentMonth.plusMonths(1)) }) {
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next", tint = colorResource(R.color.main_color_dark), modifier = Modifier.size(32.dp))
        }
    }
}

@Composable
fun DaysOfWeekRow() {
    val days = listOf("日", "一", "二", "三", "四", "五", "六")
    Row(modifier = Modifier.fillMaxWidth()) {
        days.forEach { day ->
            Text(
                text = day,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = colorResource(R.color.main_color_dark),
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun CalendarGrid(
    currentMonth: YearMonth,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfMonth = currentMonth.atDay(1)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7 // 調整為 0=日, 1=一...

    val totalSlots = 42 // 6 rows * 7 days

    // 模擬哪些日期有記錄 (圖片中 4, 8, 16 有底線)
    val hasRecordDays = listOf(4, 8, 16)

    Column {
        repeat(6) { row ->
            Row(modifier = Modifier.fillMaxWidth()) {
                repeat(7) { col ->
                    val index = row * 7 + col
                    val dayOfMonth = index - firstDayOfWeek + 1

                    if (dayOfMonth in 1..daysInMonth) {
                        val date = currentMonth.atDay(dayOfMonth)
                        val isSelected = date == selectedDate
                        val hasRecord = hasRecordDays.contains(dayOfMonth)

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            // 選中時的圓圈背景
                            if (isSelected) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(colorResource(R.color.main_color_dark), CircleShape)
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable { onDateSelected(date) },
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = dayOfMonth.toString(),
                                    color = if (isSelected) Color.White else colorResource(R.color.text_default_color),
                                    fontSize = 16.sp,
                                    fontWeight = if (isSelected || hasRecord) Bold else Normal
                                )

                                // 底部提示線 (代表有記錄)
                                if (hasRecord) {
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Box(
                                        modifier = Modifier
                                            .width(16.dp)
                                            .height(2.dp)
                                            .background(if (isSelected) Color.White else colorResource(R.color.main_color_dark))
                                    )
                                }
                            }
                        }
                    } else {
                        // 空白填充
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
package com.example.myapplication.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import com.example.myapplication.R
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.myapplication.model.DrinkRecord
import com.example.myapplication.widget.AddDrinkBottomSheet
import com.example.myapplication.widget.BorderColor
import com.example.myapplication.widget.DrinkItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSheet by remember { mutableStateOf(false) }

    // 維護飲品清單狀態
    var drinkList by remember { mutableStateOf(listOf<DrinkRecord>()) }

    // 準備圖表數據mock
    val pieChartData = PieChartData(
        slices = listOf(
            PieChartData.Slice("Google", 30f, Color(0xFF4285F4)),
            PieChartData.Slice("Facebook", 20f, Color(0xFF1877F2)),
            PieChartData.Slice("Apple", 25f, Color(0xFF555555)),
            PieChartData.Slice("Others", 25f, Color(0xFF34A853)),
        ),
        plotType = PlotType.Pie
    )

    // 準備圖表配置（例如調整切片標籤、動畫等）
    val pieChartConfig = PieChartConfig(
        backgroundColor = Color.Transparent, // 設定透明
        isAnimationEnable = true,
        showSliceLabels = true,
        animationDuration = 1500,
        labelFontSize = 14.sp,
        isClickOnSliceEnabled = true
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // 背景圖片
        Image(
            painter = painterResource(id = R.drawable.main_background), // 替換成你的圖片檔名
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.3f
        )

        // 使用 LazyColumn 讓整個頁面可以捲動
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            contentPadding = PaddingValues(bottom = 100.dp), // 預留空間給 FAB
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "本月統計",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.main_color_darker)
                )
            }

            // 放置圓形圖
            item {
                MaterialTheme(
                    colorScheme = MaterialTheme.colorScheme.copy(
                        surface = Color.Transparent // 暫時把這一區塊的 surface 設為透明
                    )
                ) {
                    PieChart(
                        modifier = Modifier
                            .width(300.dp)
                            .height(300.dp),
                        pieChartData = pieChartData,
                        pieChartConfig = pieChartConfig
                    )
                }
            }

            item {
                Text(
                    text = "今日飲品",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.main_color_darker),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // 顯示動態新增的飲品清單
            items(drinkList.size) { pos ->
                DrinkItem(
                    drink = drinkList[pos]
                )
            }
        }

        FloatingActionButton(
            onClick = {
                showSheet = true
            },
            modifier = Modifier
                .align(Alignment.BottomEnd) // 定位在右下角
                .padding(bottom = 24.dp, end = 24.dp), // 設定與底部和右邊的距離
            containerColor = colorResource(R.color.main_color_dark), // 可自訂顏色
            shape = RoundedCornerShape(99.dp)
        ) {
            // 設定 "+" 圖示
            Icon(
                imageVector = Icons.Default.Add,
                tint = Color.White,
                contentDescription = "添加"
            )

            if (showSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showSheet = false },
                    sheetState = sheetState,
                    containerColor = Color.White,
                    dragHandle = { BottomSheetDefaults.DragHandle(color = BorderColor) })
                {
                    // 傳入確認動作：更新 list 並關閉 sheet
                    AddDrinkBottomSheet(
                        onDismiss = { showSheet = false },
                        onConfirm = { newDrink ->
                            drinkList = drinkList + newDrink // 新增到清單中
                        }
                    )
                }
            }
        }
    }
}
package com.example.myapplication.screen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.myapplication.widget.AddDrinkBottomSheet
import com.example.myapplication.widget.BorderColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSheet by remember { mutableStateOf(false) }

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

        // 放置圓形圖
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "圓餅圖範例", fontSize = 20.sp, modifier = Modifier.padding(bottom = 16.dp))

            PieChart(
                modifier = Modifier
                    .width(300.dp)
                    .height(300.dp),
                pieChartData = pieChartData,
                pieChartConfig = pieChartConfig
            )
        }

        Text(text = "這是 首頁 頁面", fontSize = 24.sp, modifier = Modifier.padding(top = 16.dp))

        FloatingActionButton(
            onClick = {
                showSheet = true
            },
            modifier = Modifier
                .align(Alignment.BottomEnd) // 定位在右下角
                .padding(bottom = 24.dp, end = 24.dp), // 設定與底部和右邊的距離
            containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primary // 可自訂顏色
        ) {
            // 設定 "+" 圖示
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "添加"
            )

            if (showSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showSheet = false },
                    sheetState = sheetState,
                    containerColor = Color.White,
                    dragHandle = { BottomSheetDefaults.DragHandle(color = BorderColor) })
                {
                    AddDrinkBottomSheet(onDismiss = { showSheet = false })
                }
            }
        }
    }
}
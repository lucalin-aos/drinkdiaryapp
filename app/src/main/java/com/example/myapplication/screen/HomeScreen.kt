package com.example.myapplication.screen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Text(text = "這是 首頁 頁面", fontSize = 24.sp)

        FloatingActionButton(
            onClick = {
                // 這裡放點擊按鈕後的動作
                println("點擊了添加按鈕")
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
        }
    }
}
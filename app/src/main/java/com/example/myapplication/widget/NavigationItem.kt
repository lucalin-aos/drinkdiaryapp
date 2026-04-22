package com.example.myapplication.widget

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.ui.graphics.vector.ImageVector

enum class NavigationItem(val title: String, val icon: ImageVector, val tag: String) {
    Calendar("日曆", Icons.Default.DateRange, "calendar"),
    Home("首頁", Icons.Default.Home, "home"),
    More("更多", Icons.Default.MoreVert, "more")
}

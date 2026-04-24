package com.example.myapplication

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.screen.CalendarScreen
import com.example.myapplication.screen.HomeScreen
import com.example.myapplication.screen.MoreScreen
import com.example.myapplication.screen.ScreenRes

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InitUI()
                }
            }
        }
    }
}

@Composable
fun InitUI() {
    val navController = rememberNavController() // Compose 專屬的 NavController

    // 關鍵：監聽當前的路由狀態
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == ScreenRes.Calendar.route,
                    onClick = {
                        navController.navigate(ScreenRes.Calendar.route) {
                            // 避免返回鍵堆疊過多頁面
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.DateRange, null) },
                    label = { Text(stringResource(R.string.tab_bar_calendar)) }
                )
                NavigationBarItem(
                    selected = currentRoute == ScreenRes.Home.route,
                    onClick = {
                        navController.navigate(ScreenRes.Home.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text(stringResource(R.string.tab_bar_home)) }
                )
                NavigationBarItem(
                    selected = currentRoute == ScreenRes.More.route,
                    onClick = {
                        navController.navigate(ScreenRes.More.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.MoreHoriz, null) },
                    label = { Text(stringResource(R.string.tab_bar_more)) }
                )
                // ... 其他按鈕
            }
        }
    ) { innerPadding ->
        // 使用 Compose 原生的 NavHost，不用 AndroidView
        NavHost(
            navController = navController,
            startDestination = ScreenRes.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(ScreenRes.Home.route) { HomeScreen() }
            composable(ScreenRes.Calendar.route) { CalendarScreen() }
            composable(ScreenRes.More.route) { MoreScreen() }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        InitUI()
    }
}
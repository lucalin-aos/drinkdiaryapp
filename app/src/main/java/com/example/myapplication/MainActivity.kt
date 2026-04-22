package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import com.example.myapplication.fragment.CalendarFragment // 假設你有這些 Fragment
import com.example.myapplication.fragment.HomeFragment
import com.example.myapplication.fragment.MoreFragment
import com.example.myapplication.widget.NavigationItem

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainLayout(supportFragmentManager)
                }
            }
        }
    }
}

@Composable
fun MainLayout(fragmentManager: FragmentManager?) {
    // 2. 追蹤當前選中的分頁 (預設為首頁)
    var currentTab by remember { mutableStateOf(NavigationItem.Home) }

    Scaffold(
        bottomBar = {
            // 實作導航欄
            NavigationBar {
                NavigationItem.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = currentTab == tab,
                        onClick = { currentTab = tab },
                        label = { Text(tab.title) },
                        icon = { Icon(tab.icon, contentDescription = tab.title) }
                    )
                }
            }
        }
    ) { innerPadding ->
        // 中間區域：根據 currentTab 切換 Fragment
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            FragmentHost(
                fragmentManager = fragmentManager,
                selectedTab = currentTab
            )
        }
    }
}

@Composable
fun FragmentHost(
    modifier: Modifier = Modifier,
    fragmentManager: FragmentManager?,
    selectedTab: NavigationItem
) {
    val isPreview = LocalInspectionMode.current

    // 使用 remember 儲存一個固定的 ID，避免重組時 ID 改變
    val containerId = remember { View.generateViewId() }

    if (isPreview) {
        Text("Fragment 預覽區域")
    } else {
        AndroidView(
            modifier = modifier.fillMaxSize(),
            factory = { context ->
                FragmentContainerView(context).apply {
                    id = containerId
                }
            },
            update = { view ->
                // 當 selectedTab 改變時，這裡會被觸發
                if (fragmentManager != null) {
                    val fragment = when (selectedTab) {
                        NavigationItem.Calendar -> CalendarFragment()
                        NavigationItem.Home -> HomeFragment()
                        NavigationItem.More -> MoreFragment()
                    }

                    // 執行 Fragment 切換
                    fragmentManager.beginTransaction()
                        .replace(view.id, fragment, selectedTab.tag)
                        .setReorderingAllowed(true)
                        .commit()
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        MainLayout(fragmentManager = null)
    }
}
package com.example.myapplication

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.myapplication.fragment.HomeFragment

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                // 全螢幕容器
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
    Column(modifier = Modifier.fillMaxSize()) {
//        // 頂部 Compose 標題列
//        SmallTopAppBar(
//            title = { Text("我的 App (Compose)") },
//            colors = TopAppBarDefaults.smallTopAppBarColors(
//                containerColor = MaterialTheme.colorScheme.primaryContainer
//            )
//        )

        // 中間掛載 Fragment 的區域
        Box(
            modifier = Modifier
                .weight(1f)
                .background(colorResource(R.color.main_color_normal))
        ) {
            FragmentHost(
                fragmentManager = fragmentManager,
                fragment = HomeFragment() // 指定掛載 HomeFragment
            )
        }

        // 底部 Compose 按鈕列 (模擬導航)
        BottomAppBar {
            Text(
                "這裡可以放 Compose 的 BottomNavigation",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun FragmentHost(
    modifier: Modifier = Modifier,
    fragmentManager: FragmentManager?,
    fragment: Fragment
) {
    val isPreview = LocalInspectionMode.current

    if (isPreview) {
        // 預覽模式顯示佔位
        Surface(
            modifier = modifier.fillMaxSize(),
            color = colorResource(R.color.main_color_normal)
        ) {
            Text(
                text = "Fragment 區域",
                color = colorResource(R.color.main_color_dark)
            )
        }
    } else {
        // 實際運行
        AndroidView(
            modifier = modifier.fillMaxSize(),
            factory = { context ->
                FragmentContainerView(context).apply {
                    id = android.view.View.generateViewId()
                }
            },
            update = { view ->
                // 使用 fragmentManager 將 fragment 放入容器
                fragmentManager?.commit {
                    replace(view.id, fragment)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        MainLayout(fragmentManager = null)
    }
}
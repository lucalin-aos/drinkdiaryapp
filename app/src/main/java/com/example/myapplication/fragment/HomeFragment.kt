package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // 返回一個 ComposeView
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    // 使用 Scaffold 可以輕鬆配置 FAB (右下角按鈕)
                    Scaffold(
                        floatingActionButton = {
                            FloatingActionButton(
                                onClick = {
                                    // onclick
                                },
                                // 使用你的主色作為背景
                                containerColor = colorResource(id = R.color.main_color_dark),
                                contentColor = colorResource(R.color.main_color_dark) // 圖示顏色
                            ) {
                                Icon(Icons.Filled.Add, contentDescription = "增加")
                            }
                        }
                    ) { paddingValues ->
                        // 頁面主內容
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "本月統計",
                                fontSize = 20.sp,
                                // 使用你的主色
                                color = colorResource(id = R.color.main_color_dark),
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            }
        }
    }
}
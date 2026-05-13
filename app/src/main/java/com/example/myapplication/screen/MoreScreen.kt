package com.example.myapplication.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.BuildConfig

@Composable
fun MoreScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.main_color_normal))
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // 標題區域
        Text(
            text = stringResource(R.string.more_title),
            fontSize = 32.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.main_color_dark)
        )
        Text(
            text = stringResource(R.string.more_content),
            fontSize = 16.sp,
            color = colorResource(R.color.main_color_dark),
            modifier = Modifier.padding(top = 4.dp, bottom = 32.dp)
        )

        // 設定選單列表
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // 常用問答
            SettingItem(
                icon = Icons.Default.QuestionAnswer,
                title = stringResource(R.string.more_column_faq),
                onClick = { /* 點擊動作 */ }
            )
            // 通知提醒
            SettingItem(
                icon = Icons.Default.NotificationsNone,
                title = stringResource(R.string.more_column_notify),
                onClick = { /* 點擊動作 */ }
            )
            // 聯絡我們
            SettingItem(
                icon = Icons.Default.MailOutline,
                title = stringResource(R.string.more_column_contact),
                onClick = { /* 點擊動作 */ }
            )
            // 版本號碼
            SettingItem(
                icon = Icons.Default.Info,
                title = stringResource(R.string.more_column_version_num),
                trailingText = BuildConfig.VERSION_NAME,
                showArrow = false
            )
        }
    }
}

@Composable
fun SettingItem(
    icon: ImageVector,
    title: String,
    trailingText: String? = null,
    showArrow: Boolean = true,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable(enabled = showArrow) { onClick() },
        shape = RoundedCornerShape(24.dp),
        color = colorResource(R.color.white)
    ) {
        // item layout
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 左側圖示外框
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        colorResource(R.color.main_color_normal),
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = colorResource(R.color.main_color_dark),
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // 中間標題
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.main_color_dark),
                modifier = Modifier.weight(1f)
            )

            // 右側內容（箭頭或版本號）
            if (trailingText != null) {
                Text(
                    text = trailingText,
                    fontSize = 16.sp,
                    color = colorResource(R.color.main_color_dark)
                )
            }

            if (showArrow) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = colorResource(R.color.main_color_dark).copy(alpha = 0.6f)
                )
            }
        }
    }
}
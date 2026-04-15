package com.example.myapplication.util

import android.os.SystemClock
import android.view.View

/***
 * 防止快速連點的 ClickListener。
 * @param defaultInterval 預設的點擊間隔時間，單位為毫秒。預設值為 1000 毫秒 (1 秒)。
 * @param onSafeCLick 實際的點擊事件處理函數。
 */
class SafeClickListener(
    private var defaultInterval: Int = 1000, // 預設點擊間隔為 1000 毫秒 (1 秒)
    private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {

    private var lastTimeClicked: Long = 0

    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return // 如果間隔時間不足，則忽略此次點擊
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v) // 執行實際的點擊邏輯
    }
}
package com.example.myapplication.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.format.DateUtils
import android.text.style.ClickableSpan
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.IdRes
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import androidx.core.graphics.toColorInt
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import com.example.myapplication.util.SafeClickListener

// region Int

fun Int.convertToThousandSeparatorNumberString(): String {
    val formatter = DecimalFormat("#,###")
    return formatter.format(this)
}

// endregion

//region long

fun Long.toTimerFormat(): String { //trans long to MM:SS
    return DateUtils.formatElapsedTime(this)
}

// endregion

// region String

fun String.convertToThousandSeparatorNumberString(): String {
    val formatter = DecimalFormat("#,###")
    return formatter.format(Integer.parseInt(this))
}

//日期字串轉Calendar
fun String.toCalendar(patternString: String): Calendar? {
    return try {
        // 將日期字串轉換成 Date
        val date = SimpleDateFormat(patternString, Locale.getDefault()).parse(this)
        if (date != null) {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar
        } else {
            null
        }
    } catch (e: ParseException) {
        Log.d("Extension", "ParseException: " + e.message)
        null
    }
}

// 檢查色碼格式(6碼或8碼)
fun String?.toValidColor(defaultColor: Int): Int {
    this?.let {
        return if ((this.length == 6 || this.length == 8) &&
            this.matches(Regex("^[0-9a-fA-F]{6,8}$"))
        ) {
            "#$this".toColorInt()
        } else {
            defaultColor
        }
    } ?: run {
        return defaultColor
    }
}

// endregion

// region SpannableString

//設定指定字段的點擊事件、顏色等
fun SpannableString.setTextEvent(
    targetText: String,
    textColor: Int? = null,
    textSize: Float? = null,
    isUnderLine: Boolean = true,
    clickEvent: (() -> Unit)? = null
): SpannableString {
    val index = this.indexOf(targetText)
    if (index != -1) {
        this.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                // 設定點擊事件
                clickEvent?.invoke()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                //設定文字顏色，null不改顏色
                textColor?.let { ds.color = it }
                //設定文字大小，null不改大小
                textSize?.let {
                    ds.textSize = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_SP,
                        it,
                        Resources.getSystem().displayMetrics
                    )
                }
                //加底線
                ds.isUnderlineText = isUnderLine
            }
        }, index, index + targetText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    return this
}

// endregion

// region Context

//檢查context是否有效
fun Context?.isValid(): Boolean {
    return !(this == null || this is Activity && (this.isFinishing || this.isDestroyed))
}

//置中顯示Toast
fun Context.showMiddleToast(message: String) {
    if (this.isValid()) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}

fun Context.getNavigationBarHeight(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        // 這裡要從 WindowManager 取 windowMetrics
        val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val insets = windowManager.currentWindowMetrics.windowInsets
        val navigationBarInsets = insets.getInsets(WindowInsets.Type.navigationBars())
        navigationBarInsets.bottom
    } else {
        val resourceId = this.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            this.resources.getDimensionPixelSize(resourceId)
        } else {
            0
        }
    }
}

// 取得手機剪貼簿複製到的文字
fun Context?.getClipboardText(): String {
    val clipboard = this?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = clipboard.primaryClip

    if (clipData != null && clipData.itemCount > 0) {
        val text = clipData.getItemAt(0).coerceToText(this).toString()
        return text.ifBlank { "" }
    }
    return ""
}

// 取得裝置導航模式(手勢操作 = 2/三按鈕 = 0)
fun Context.getNavigationMode(): Int {
    return Settings.Secure.getInt(
        this.contentResolver,
        "navigation_mode",
        0
    )
}

// endregion

// region View

fun View.slideInHorizontally() {
    this.animate()
        .translationX(0f)
        .setDuration(200)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                isVisible = true
            }
        })
}

fun View.slideOutHorizontally() {
    this.animate()
        .translationX(-width.toFloat())
        .setDuration(200)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                isVisible = false
            }
        })
}

fun View.slideInVertically() {
    this.animate()
        .translationY(0f)
        .setDuration(200)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                isVisible = true
            }
        })
}

fun View.slideOutVertically() {
    this.animate()
        .translationY(height.toFloat())
        .setDuration(200)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                isVisible = false
            }
        })
}


fun View.applyTopInsetPadding(enabled: Boolean) {
    // android 15以上的edge-to-edge模式會影響畫面重疊到status bar,需動態調整
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
        if (enabled) {
            ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
                val topInset = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
                v.setPadding(0, topInset, 0, 0)
                insets
            }
        } else {
            ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
                v.setPadding(0, 0, 0, 0)
                insets
            }
        }
    }
    ViewCompat.requestApplyInsets(this)
}

fun View.applyBottomInsetPadding(enabled: Boolean) {
    // android 15以上的edge-to-edge模式會影響畫面重疊到system bar,需動態調整
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
        if (enabled) {
            ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
                val bottomInset = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
                v.setPadding(0, 0, 0, bottomInset)
                insets
            }
        } else {
            ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
                v.setPadding(0, 0, 0, 0)
                insets
            }
        }
    }
}

fun View.applyVerticalInsetPadding(enabled: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
        if (enabled) {
            ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
                val topInset = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
                val bottomInset = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
                v.setPadding(0, topInset, 0, bottomInset)
                insets
            }
        } else {
            ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
                v.setPadding(0, 0, 0, 0)
                insets
            }
        }
    }
    ViewCompat.requestApplyInsets(this)
}

//防止double click
fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

// endregion

// region Navigation Extensions

/**
 * 較安全的 navigate，統一處理所有異常情況，失敗時僅留 log 不 crash。
 *
 * 處理情境：
 * - currentDestination 為 null（NavController 尚未就緒）→ log + 不導頁
 * - currentDestination 不符預期（重複指令、已離開當前頁）→ 不導頁、不 log（正常略過）
 * - actionId 不存在或其他 navigate 例外 → log + 不導頁
 *
 * @param currentDestinationId 預期的當前 destination id（通常為呼叫端 Fragment 的 id）
 * @param actionId 要執行的 navigation action id
 * @param args 傳遞至目標 destination 的參數
 */
fun NavController.safeNavigate(
    @IdRes currentDestinationId: Int,
    @IdRes actionId: Int,
    args: Bundle? = null
) {
    val current = currentDestination
    if (current == null) {
        Log.e("safeNavigate", "currentDestination 為 null，無法執行 navigate(actionId=$actionId)")
        return
    }
    if (current.id != currentDestinationId) return
    try {
        navigate(actionId, args)
    } catch (e: Exception) {
        Log.e("safeNavigate", "navigate 失敗：actionId=$actionId，from=${current.label}(${current.id})", e)
    }
}

// endregion
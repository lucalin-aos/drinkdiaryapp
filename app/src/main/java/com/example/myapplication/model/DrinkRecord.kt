package com.example.myapplication.model

/**
 * 首頁用的飲品清單項目
 */
data class DrinkRecord(
    val id: Long = System.currentTimeMillis(), // 用時間當簡單的 ID
    val name: String,
    val price: String,
    val calories: String,
    val category: String,
    val temperature: String
)

package com.example.myapplication.screen

sealed class ScreenRes(val route: String) {
    object Home : ScreenRes("home")
    object Calendar : ScreenRes("calendar")
    object More : ScreenRes("more")
}
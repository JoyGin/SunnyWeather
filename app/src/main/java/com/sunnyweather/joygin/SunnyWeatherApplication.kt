package com.sunnyweather.joygin

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication : Application() {
    companion object{
//        const val TOKEN = "令牌值"
        const val TOKEN = "hMy4wWdA0iyKrMky"
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}
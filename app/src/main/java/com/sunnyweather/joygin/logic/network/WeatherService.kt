package com.sunnyweather.joygin.logic.network

import com.sunnyweather.joygin.SunnyWeatherApplication
import com.sunnyweather.joygin.logic.model.DailyRespone
import com.sunnyweather.joygin.logic.model.RealtimeRespone
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {
    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String):Call<RealtimeRespone>

    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String):Call<DailyRespone>

}
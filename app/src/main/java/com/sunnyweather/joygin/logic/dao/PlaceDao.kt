package com.sunnyweather.joygin.logic.dao

import android.content.Context
import com.google.gson.Gson
import com.sunnyweather.joygin.SunnyWeatherApplication
import com.sunnyweather.joygin.logic.model.Place

object PlaceDao {

    private fun sharedPreferences() = SunnyWeatherApplication.context.
        getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)

    fun isPlaceSaved() = sharedPreferences().contains("place")

    fun savePlace(place: Place){
        val editor = sharedPreferences().edit()
        editor.apply{
            putString("place", Gson().toJson(place))
        }.commit()
    }

    fun getSavedPlace(): Place {
        val placeJson = sharedPreferences().getString("place","")
        return Gson().fromJson(placeJson, Place::class.java)
    }
}
package com.sunnyweather.joygin.logic

import androidx.lifecycle.liveData
import com.sunnyweather.joygin.logic.dao.PlaceDao
import com.sunnyweather.joygin.logic.model.Place
import com.sunnyweather.joygin.logic.model.Weather
import com.sunnyweather.joygin.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import kotlin.RuntimeException

object Repository {

    fun searchPlaces(query: String) = liveData(Dispatchers.IO){
        val result = try{
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok"){
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        }catch (e: Exception){
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }

    fun refreshWeather(lng: String, lat: String) = liveData(Dispatchers.IO){
        val result = try {
            coroutineScope {
                val defferedRealtime = async {
                    SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
                }
                val defferedDaily = async {
                    SunnyWeatherNetwork.getDailyWeather(lng, lat)
                }
                val realtimeRespone = defferedRealtime.await()
                val dailyRespone = defferedDaily.await()
                if (realtimeRespone.status == "ok" && dailyRespone.status == "ok"){
                    val weather = Weather(realtimeRespone.result.realtime, dailyRespone.result.daily)
                    Result.success(weather)
                }else{
                    Result.failure(
                        RuntimeException(
                            "realtime response status is ${realtimeRespone.status}" +
                                    "daily response status is ${dailyRespone.status}"
                        )
                    )
                }
            }
        }catch(e: Exception){
            Result.failure<Weather>(e)
        }
        emit(result)
    }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun  getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}
package com.example.theweather.data.remote

import com.example.theweather.constants.Constants.Companion.apiKey
import com.example.theweather.data.models.ForecastResult
import com.example.theweather.data.models.WeatherResult
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double = 0.0,
        @Query("longitude") lng: Double = 0.0,
        @Query("units") units: String = "metric",
        @Query("app") appId: String = apiKey
    ): WeatherResult

    @GET("forecast")
    suspend fun getForecast(
        @Query("latitude") lat: Double = 0.0,
        @Query("longitude") lng: Double = 0.0,
        @Query("units") units: String = "metric",
        @Query("app") appId: String = apiKey
    ): ForecastResult

}
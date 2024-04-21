package com.example.theweather.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.nio.file.WatchEvent

interface RetrofitClient {
    companion object {
        private var apiService: WeatherApi? = null
        fun getInstance(): WeatherApi {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org/data/2.5/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(WeatherApi::class.java)
            }
            return apiService!!
        }
    }
}
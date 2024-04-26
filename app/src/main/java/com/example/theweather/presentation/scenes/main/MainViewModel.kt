package com.example.theweather.presentation.scenes.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theweather.data.models.LatLng
import com.example.theweather.data.models.WeatherResult
import com.example.theweather.data.remote.RetrofitClient
import kotlinx.coroutines.launch
import java.lang.Exception

enum class STATE {
    LOADING,
    SUCCESS,
    FAILED
}

class MainViewModel: ViewModel() {
    var state by mutableStateOf(STATE.LOADING)
    var weatherResponse: WeatherResult by mutableStateOf(WeatherResult())
    var errorMessage: String by mutableStateOf("")

    fun getWeatherByLocation(latLng: LatLng){
        viewModelScope.launch {
            state = STATE.LOADING
            val apiService = RetrofitClient.getInstance()
            try {
                val apiResponse = apiService.getWeatherData(latLng.lat, latLng.lng)
                weatherResponse = apiResponse
                state = STATE.SUCCESS
            } catch (e: Exception) {
                errorMessage = e.message.toString()
                state = STATE.FAILED
            }
        }
    }
}
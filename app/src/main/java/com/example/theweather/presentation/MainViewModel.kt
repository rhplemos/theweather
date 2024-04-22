package com.example.theweather.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theweather.constants.StaticCoordinates
import com.example.theweather.data.models.ForecastResult
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
    var citiesWeathers: MutableList<WeatherResult> by mutableStateOf(ArrayList<WeatherResult>())
    var forecastResponse: ForecastResult by mutableStateOf(ForecastResult())
    var errorMessage: String by mutableStateOf("")
    private var staticLocations = mutableListOf(
        StaticCoordinates.MONTEVIDEO,
        StaticCoordinates.LONDRES,
        StaticCoordinates.SAO_PAULO,
        StaticCoordinates.BUENOS_AIRES,
        StaticCoordinates.MUNICH
    )

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

    fun getForecastByLocation(latLng: LatLng) {
        viewModelScope.launch {
            state = STATE.LOADING
            val apiService = RetrofitClient.getInstance()
            try {
                val apiResponse = apiService.getForecast(latLng.lat, latLng.lng)
                forecastResponse = apiResponse
                state = STATE.SUCCESS
            } catch (e: Exception) {
                errorMessage = e.message.toString()
                state = STATE.FAILED
            }
        }
    }

    fun getCitiesWeathers() {
        viewModelScope.launch {
            val response: MutableList<WeatherResult> = mutableListOf()

            val apiService = RetrofitClient.getInstance()
            try {
                staticLocations.forEach { loc ->
                    response.add(apiService.getWeatherData(loc.lat, loc.lng))
                }

                citiesWeathers = response
                state = STATE.SUCCESS
            } catch (e: Exception) {
                errorMessage = e.message.toString()
                state = STATE.FAILED
            }
        }
    }
}
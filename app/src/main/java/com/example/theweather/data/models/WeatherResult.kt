package com.example.theweather.data.models
import com.google.gson.annotations.SerializedName

data class WeatherResult(
    @SerializedName("coord") var coord: Coord,
    @SerializedName("weather") var weather: ArrayList<WeatherDataResult>? = arrayListOf(),
    @SerializedName("base") var base: String,
    @SerializedName("main") var main: Main? = Main(),
    @SerializedName("visibility") var visibility: Int? = null,
    @SerializedName("wind") var wind: Wind? = Wind(),
    @SerializedName("wind") var cloud: Clouds? = Clouds(),
    @SerializedName("dt") var dt: Int? = null,
    @SerializedName("sys") var sys: Sys? = Sys(),
    @SerializedName("timezone") var timezone: Int? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: Int? = null,
    @SerializedName("cod") var cod: Int? = null,
    @SerializedName("snow") var snow: Snow? = Snow(),

    )
package com.example.theweather.data.models

import com.google.gson.annotations.SerializedName

data class CustomList (
    @SerializedName("dt") var dt: Int? = null,
    @SerializedName("main") var main: Int? = null,
    @SerializedName("weather") var weather: ArrayList<WeatherDataResult>? = arrayListOf(),
    @SerializedName("clouds") var clouds: Clouds? = Clouds(),
    @SerializedName("wind") var wind: Wind? = Wind(),
    @SerializedName("visibility") var visibility: Int? = null,
    @SerializedName("pop") var pop: Double? = null,
    @SerializedName("sys") var sys: Sys? = Sys(),
    @SerializedName("dt_txt") var dt_txt: String? = null,


    )


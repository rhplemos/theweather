package com.example.theweather.data.models

import com.google.gson.annotations.SerializedName

class WeatherDataResult(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("main")
    var main: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("icon")
    var icon: String? = null,
)
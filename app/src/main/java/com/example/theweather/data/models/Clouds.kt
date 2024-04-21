package com.example.theweather.data.models

import com.google.gson.annotations.SerializedName

data class Clouds (
    @SerializedName("all") var all: Int? = null,
)

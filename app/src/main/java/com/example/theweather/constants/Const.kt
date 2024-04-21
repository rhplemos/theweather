package com.example.theweather.constants

class Const {
    companion object {
        const val apiKey = "97c8b03b803f52c9f70ac609aa5d75d4"
        val permissions = arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        )

        const val LOADING = "Loading..."
        const val NA = "N/A"
    }
}
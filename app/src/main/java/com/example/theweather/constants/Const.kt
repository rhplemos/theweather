package com.example.theweather.constants

class Const {
    companion object {
        const val apiKey = "5fc067d4560445e14a15437ed346ea95"
        val permissions = arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        )

        const val LOADING = "Loading..."
        const val NA = "N/A"
    }
}
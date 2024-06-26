package com.example.theweather.constants
import com.example.theweather.data.models.LatLng

interface StaticCoordinates {
    companion object {
        val MONTEVIDEO = LatLng(-34.90556, -56.18561)
        val LONDRES = LatLng(51.5074, -0.1278)
        val SAO_PAULO = LatLng(-23.5505, -46.6333)
        val BUENOS_AIRES = LatLng(-34.6037, -58.3816)
        val MUNICH = LatLng(48.13490, 11.58206)
    }
}

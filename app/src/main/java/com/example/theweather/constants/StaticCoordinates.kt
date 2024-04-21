package com.example.theweather.constants
import com.example.theweather.data.models.LatLng


interface StaticCoordinates {
    companion object {
        val MONTEVIDEO = LatLng(-34.8833,-56.1667)
        val LONDRES = LatLng( 51.5072, -0.1275)
        val SAO_PAULO = LatLng( -23.5489,-46.6388)
        val BUENOS_AIRES = LatLng(-34.6083,-58.3712)
        val MUNICH = LatLng(48.1369,11.5753)
    }
}
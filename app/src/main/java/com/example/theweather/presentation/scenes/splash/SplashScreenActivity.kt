package com.example.theweather.presentation.scenes.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.theweather.constants.StaticCoordinates
import com.example.theweather.presentation.ui.theme.WeatherAppTheme
import com.example.theweather.data.models.LatLng
import com.example.theweather.presentation.scenes.main.MainActivity
import com.example.theweather.presentation.ui.theme.colorBg1
import com.example.theweather.presentation.ui.theme.colorBg2

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                SplashScreenContent(this)
            }
        }
    }
}

@Composable
fun SplashScreenContent(context: Context) {
    val gradient = Brush.linearGradient(
        colors = listOf(colorBg1, colorBg2),
        start = Offset(1000f, -1000f),
        end = Offset(1000f, 1000f)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Select the location:",
            modifier = Modifier.padding(16.dp),
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        CityButton(
            cityName = "My location",
            coordinates = LatLng(0.0, 0.0),
            isUserLocation = true,
            context = context
        )
        CityButton(
            cityName = "São Paulo",
            coordinates = StaticCoordinates.SAO_PAULO,
            context = context
        )
        CityButton(
            cityName = "Montevideo",
            coordinates = StaticCoordinates.MONTEVIDEO,
            context = context
        )
        CityButton(
            cityName = "Buenos Aires",
            coordinates = StaticCoordinates.BUENOS_AIRES,
            context = context
        )
        CityButton(
            cityName = "Munich",
            coordinates = StaticCoordinates.MUNICH,
            context = context
        )
        CityButton(
            cityName = "Londres",
            coordinates = StaticCoordinates.LONDRES,
            context = context
        )
    }
}

@Composable
fun CityButton(
    cityName: String,
    coordinates: LatLng,
    isUserLocation: Boolean? = false,
    context: Context
) {
    Button(
        onClick = {
            startMainActivity(coordinates, isUserLocation, context)
        },
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1B3B5A)),
    ) {
        Text(
            text = cityName,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

fun startMainActivity(
    coordinates: LatLng,
    isUserLocation: Boolean?,
    context: Context
) {
    val intent = Intent(context, MainActivity::class.java)
    intent.putExtra("selectedLat", coordinates.lat)
    intent.putExtra("selectedLong", coordinates.lng)
    intent.putExtra("isUserLocation", isUserLocation)
    startActivity(context, intent, null)
}
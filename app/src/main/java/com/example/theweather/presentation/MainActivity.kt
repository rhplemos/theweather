package com.example.theweather.presentation

import ErrorSection
import ForecastSection
import LoadingSection
import WeatherSection
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.theweather.constants.Const.Companion.permissions
import com.example.theweather.data.models.ForecastResult
import com.example.theweather.data.models.LatLng
import com.example.theweather.data.models.WeatherResult
import com.example.theweather.presentation.ui.theme.WeatherAppTheme
import com.example.theweather.presentation.ui.theme.colorBg1
import com.example.theweather.presentation.ui.theme.colorBg2
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var locationRequired: Boolean = false
    private lateinit var mainViewModel: MainViewModel

    override fun onPause() {
        super.onPause()
        locationCallback.let {
            fusedLocationProviderClient.removeLocationUpdates(it)
        }
    }

    override fun onResume() {
        super.onResume()
        if (locationRequired) startLocationUpdate()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdate() {
        locationCallback.let {
            val locationRequest = LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 3000
                maxWaitTime = 100
                fastestInterval = 3000
                smallestDisplacement = 0f
            }

            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLocationClient()
        initViewModel()

        setContent {
            var currentLocation by remember {
                mutableStateOf(LatLng(0.0, 0.0))
            }

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)
                    for (location in p0.locations) {
                        currentLocation = LatLng(
                            location.latitude,
                            location.longitude
                        )
                    }

                    featchWeatherInformation(mainViewModel, currentLocation)
                }
            }

            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Blue
                ) {
                    LocationScreen(this@MainActivity, currentLocation)
                }
            }
        }
    }

    private fun featchWeatherInformation(mainViewModel: MainViewModel, currentLocation: LatLng) {
        mainViewModel.state = STATE.LOADING
        mainViewModel.getWeatherByLocation(currentLocation)
//        mainViewModel.getForecastByLocation(currentLocation)
        mainViewModel.state = STATE.SUCCESS
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]
    }

    @Composable
    private fun LocationScreen(context: Context, currentLocation: LatLng) {
        val launcherMultiplePermissions = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionMap ->
            val areGranted = permissionMap.values.reduce { accepted, next ->
                accepted && next
            }
            if (areGranted) {
                locationRequired = true;
                startLocationUpdate();
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

        LaunchedEffect(key1 = currentLocation, block = {
            coroutineScope {
                if (permissions.all {
                        ContextCompat.checkSelfPermission(
                            context,
                            it
                        ) == PackageManager.PERMISSION_GRANTED
                    }) {
                    startLocationUpdate()
                } else {
                    launcherMultiplePermissions.launch(permissions)
                }
            }
        })

        val gradient = Brush.linearGradient(
            colors = listOf(colorBg1, colorBg2),
            start = Offset(1000f, -1000f),
            end = Offset(1000f, 1000f)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
        ) {
            val screenHeight = LocalConfiguration.current.screenHeightDp.dp
            val marginTop = screenHeight * 0.2f
            val marginTopPx = with(LocalDensity.current) {
                marginTop.toPx()
            }

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)

                        layout(
                            placeable.width,
                            placeable.height + marginTopPx.toInt()
                        ) {
                            placeable.placeRelative(0, marginTopPx.toInt())
                        }
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                when (mainViewModel.state) {
                    STATE.LOADING -> {
                        LoadingSection()
                    }
                    STATE.FAILED -> {
                        ErrorSection(mainViewModel.errorMessage)
                    }
                    else -> {
                        WeatherSection(mainViewModel.weatherResponse)
                        ForecastSection(mainViewModel.forecastResponse)
                    }
                }
            }

        }
    }

    private fun initLocationClient() {
        fusedLocationProviderClient = LocationServices
            .getFusedLocationProviderClient(this)
    }

    @Preview
    @Composable
    fun previewHome() {
        LocationScreen(this@MainActivity, LatLng(11.11, 11.11))
    }
}
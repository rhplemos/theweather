package com.example.theweather.presentation

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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.example.theweather.constants.Const.Companion.permissions
import com.example.theweather.data.models.MyLatLng
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

    override fun onPause() {
        super.onPause()
        locationCallback.let {
            fusedLocationProviderClient.removeLocationUpdates(it)
        }
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
        setContent {
            var currentLocation by remember {
                mutableStateOf(MyLatLng(0.0, 0.0))
            }

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)
                    for (location in p0.locations) {
                        currentLocation = MyLatLng(
                            location.latitude,
                            location.longitude
                        )
                    }
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

    @Composable
    private fun LocationScreen(context: Context, currentLocation: MyLatLng) {
        val launcherMultiplePermissions = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionMap ->
            val areGranted = permissionMap.values.reduce {
                    accepted, next -> accepted && next
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
                        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
                    }) {
                    startLocationUpdate()
                }
                else {
                    launcherMultiplePermissions.launch(permissions)
                }
            }
        })

        val gradient = Brush.linearGradient(
            colors = listOf(colorBg1, colorBg2),
            start = Offset(1000f, -1000f),
            end = Offset(1000f, 1000f)
        )

        Box(modifier = Modifier.fillMaxSize()
            .background(gradient)) {

        }
    }

    private fun initLocationClient() {
        fusedLocationProviderClient = LocationServices
            .getFusedLocationProviderClient(this)
    }
}
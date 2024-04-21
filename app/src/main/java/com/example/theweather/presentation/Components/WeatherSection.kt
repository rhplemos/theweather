package com.example.theweather.presentation.Components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theweather.R
import com.example.theweather.constants.Const
import com.example.theweather.data.models.WeatherResult
import com.example.theweather.domain.util.Utils.Companion.timestampToHumanDate

@Composable
fun WeatherSection(
    data: WeatherResult,
) {
    val title = setTitle(data)
    val subTitle = setSubtitle(data)
    val temp = setTemperature(data)
    val maxTemp = setMaxTemperature(data)
    val minTemp = setMinTemperature(data)
    val description = setDescription(data)
    val wind = setWind(data)

    data.let { data ->
        Card(
            backgroundColor = Color(0xFF1B3B5A),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherTitleSection(text = title, subText = subTitle, fontSize = 30.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${temp}",
                    fontSize = 50.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = description,
                    fontSize = 20.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherTemperatureDisplay(
                        value = minTemp,
                        label = "Min",
                        textStyle = TextStyle(color = Color.White)
                    )
                    WeatherTemperatureDisplay(
                        value = maxTemp,
                        label = "Max",
                        textStyle = TextStyle(color = Color.White)
                    )
                    WeatherDataDisplay(
                        value = wind,
                        icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
                        iconTint = Color.White,
                        textStyle = TextStyle(color = Color.White)
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherTitleSection(text: String, subText: String, fontSize: TextUnit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = text, fontSize = fontSize, color = Color.White, fontWeight = FontWeight.Bold)
        Text(text = subText, fontSize = 14.sp, color = Color.White)
    }
}

private fun setTitle(weatherResponse: WeatherResult): String {
    return if (!weatherResponse.name.isNullOrEmpty()) {
        weatherResponse.name.let {
            it!!
        }
    } else {
        weatherResponse.coord.let {
            "${it!!.lat}/${it.lon}"
        }
    }
}

private fun setSubtitle(weatherResponse: WeatherResult): String {
    val dateVal = (weatherResponse.dt ?: 0)

    return if (dateVal == 0) {
        Const.LOADING
    } else {
        timestampToHumanDate(dateVal.toLong(), "dd-MM-yyyy")
    }
}

private fun setIcon(weatherResponse: WeatherResult): String {
    var icon = ""

    weatherResponse.weather.let {
        if (it!!.size > 0) {
            icon = if (it.first().icon == null) {
                Const.LOADING
            } else {
                it.first().icon!!
            }
        }
    }

    return icon
}

private fun setTemperature(weatherResponse: WeatherResult): String {
    var temp = ""

    weatherResponse.main.let {
        temp = "${it!!.temp} ºC"
    }

    return temp
}

private fun setDescription(weatherResponse: WeatherResult): String {
    var description = ""

    weatherResponse.weather.let {
        if (it!!.size > 0) {
            description = if (it.first().description == null) {
                Const.LOADING
            } else {
                it.first().description!!
            }
        }
    }

    return description
}

private fun setWind(weatherResponse: WeatherResult): String {
    var wind = ""

    weatherResponse.wind.let {
        wind = if (it == null) {
            Const.LOADING
        } else {
            "${it.speed}"
        }
    }

    return wind
}

private fun setClouds(weatherResponse: WeatherResult): String {
    var clouds = ""

    weatherResponse.cloud.let {
        clouds = if (it == null) {
            Const.LOADING
        } else {
            if (it.all == null) {
                "N/A"
            } else {
                "${it.all}"
            }
        }
    }

    return clouds
}

private fun setMinTemperature(weatherResponse: WeatherResult): String {
    var min = ""

    weatherResponse.main.let {
        min = "${it!!.temp_min} ºC"
    }

    return min
}

private fun setMaxTemperature(weatherResponse: WeatherResult): String {
    var max = ""

    weatherResponse.main.let {
        max = "${it!!.temp_max} ºC"
    }

    return max
}
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.theweather.constants.Const.Companion.LOADING
import com.example.theweather.data.models.WeatherResult
import com.example.theweather.domain.util.Utils.Companion.buildIcon
import com.example.theweather.domain.util.Utils.Companion.timestampToHumanDate

@Composable
fun WeatherSectiona(weatherResponse: WeatherResult) {
    val title = setTitle(weatherResponse)
    val subTitle = setSubtitle(weatherResponse)
    val icon = setIcon(weatherResponse)
    val temp = setTemperature(weatherResponse)
    val maxTemp = setMaxTemperature(weatherResponse)
    val minTemp = setMinTemperature(weatherResponse)
    val description = setDescription(weatherResponse)
    val wind = setWind(weatherResponse)
    val clouds = setClouds(weatherResponse)
    
//    WeatherCard(data = weatherResponse)

//    WeatherTitleSection(text = title, subText = subTitle, fontSize = 30.sp)
//    Spacer(Modifier.size(16.dp))
//    WeatherTitleSection(text = temp, subText = description, fontSize = 30.sp)
//    Spacer(Modifier.size(16.dp))
//    
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        horizontalArrangement = Arrangement.SpaceAround
//    ) {
//        MinAndMaxeSection(temp = minTemp, fontSize = 25.sp, isMin = true)
//        MinAndMaxeSection(temp = maxTemp, fontSize = 25.sp, isMin = false)
//    }
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        horizontalArrangement = Arrangement.SpaceAround
//    ) {
//        WeatherInfo(icon = R.drawable.ic_wind, text = wind)
//        WeatherInfo(icon = R.drawable.ic_cloudy, text = clouds)
//    }

}

@Composable
fun WeatherInfo(icon: Int, text: String) {
    Column(

    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.width(40.dp)
        )
        Text(text = text, fontSize = 24.sp, color = Color.White)

    }
}

@Composable
fun WeatherImage(icon: String) {
    AsyncImage(
        model = buildIcon(icon),
        contentDescription = icon,
        modifier = Modifier
            .width(200.dp)
            .height(200.dp),
        contentScale = ContentScale.FillBounds
    )
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

@Composable
fun MinAndMaxeSection(temp: String, fontSize: TextUnit, isMin: Boolean) {
    val minAndMaxText = if (isMin) {
        "Min"
    } else {
        "Max"
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(text = minAndMaxText, fontSize = 14.sp, color = Color.White, textAlign = TextAlign.Center)
        Text(text = temp, fontSize = fontSize, color = Color.White, fontWeight = FontWeight.Bold)
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
        LOADING
    } else {
        timestampToHumanDate(dateVal.toLong(), "dd-MM-yyyy")
    }
}

private fun setIcon(weatherResponse: WeatherResult): String {
    var icon = ""

    weatherResponse.weather.let {
        if (it!!.size > 0) {
            icon = if (it.first().icon == null) {
                LOADING
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
        temp = "${it!!.temp}"
    }

    return temp
}

private fun setDescription(weatherResponse: WeatherResult): String {
    var description = ""

    weatherResponse.weather.let {
        if (it!!.size > 0) {
            description = if (it.first().description == null) {
                LOADING
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
            LOADING
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
            LOADING
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
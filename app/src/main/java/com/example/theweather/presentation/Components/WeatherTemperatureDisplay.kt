package com.example.theweather.presentation.Components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun WeatherTemperatureDisplay(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(),
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = textStyle
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = value,
            style = textStyle
        )
    }
}
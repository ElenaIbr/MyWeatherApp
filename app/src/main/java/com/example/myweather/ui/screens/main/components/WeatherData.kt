package com.example.myweather.ui.screens.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.myweather.R
import com.example.myweather.domain.models.WeatherCoordinates
import com.example.myweather.domain.models.WeatherModel
import com.example.myweather.ui.screens.main.viewmodel.MainScreenViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun WeatherData() {
    val viewModel: MainScreenViewModel = hiltViewModel()
    val mainScreenState by viewModel.mainScreenState.collectAsState()

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.White,
            darkIcons = true
        )
    }

    val defaultWeather = remember {
        mutableStateOf(
            WeatherCoordinates(
                lat = "52.364138",
                lon = "4.891697"
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(
                vertical = 24.dp,
                horizontal = 16.dp
            )
    ) {
        mainScreenState.currentWeather?.timezone?.let { timeZone ->
            Text(
                text = "Location: $timeZone",
                style = MaterialTheme.typography.subtitle1
            )
        }

        mainScreenState.currentWeather?.current?.weather?.first().let { weather ->
            Text(
                text = "Description: ${weather?.description}",
                style = MaterialTheme.typography.subtitle1
            )
        }

        mainScreenState.currentWeather?.current?.weather?.first()?.icon?.let { url ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier.size(96.dp),
                    model = url,
                    contentDescription = null,
                )
            }
        }

        PrimaryTextField(
            label = stringResource(id = R.string.latitude),
            value = mainScreenState.lat ?: "",
            onValueChange = {
                viewModel.updateLat(it)
            }
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        PrimaryTextField(
            label = stringResource(id = R.string.longitude),
            value = mainScreenState.lon ?: "",
            onValueChange = {
                viewModel.updateLon(it)
            }
        )
        Spacer(
            modifier = Modifier.height(32.dp)
        )
        PrimaryButton(
            text = "Update weather",
            onClick = {
                viewModel.fetchWeather()
            }
        )
    }
}

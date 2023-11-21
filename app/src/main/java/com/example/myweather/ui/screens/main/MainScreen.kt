package com.example.myweather.ui.screens.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.myweather.R
import com.example.myweather.ui.screens.main.components.PrimaryButton
import com.example.myweather.ui.screens.main.components.PrimaryTextField
import com.example.myweather.ui.screens.main.viewmodel.MainScreenViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainScreen() {
    val viewModel: MainScreenViewModel = hiltViewModel()
    val mainScreenState by viewModel.mainScreenState.collectAsState()
    val systemUiController = rememberSystemUiController()
    val context = LocalContext.current

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.White,
            darkIcons = true
        )
        systemUiController.setNavigationBarColor(
            color = Color.White,
            darkIcons = true
        )
    }

    LaunchedEffect(
        key1 = mainScreenState.currentWeather?.lat,
        key2 = mainScreenState.currentWeather?.lon
    ) {
        mainScreenState.currentWeather?.lat?.let { lat ->
            mainScreenState.currentWeather?.lon?.let { lon ->
                viewModel.getAddressByLocationUseCase(lat, lon)
            }
        }
    }

    if (mainScreenState.currentWeather == null && !mainScreenState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.unable_get_weather),
                style = MaterialTheme.typography.subtitle2
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(
                    top = dimensionResource(id = R.dimen.padding_48),
                    start = dimensionResource(id = R.dimen.padding_16),
                    end = dimensionResource(id = R.dimen.padding_16)
                )
        ) {
            //Timezone
            mainScreenState.currentWeather?.timezone?.let { timeZone ->
                Text(
                    text = stringResource(id = R.string.current),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold
                )
                Spacer(
                    modifier = Modifier.height(dimensionResource(id = R.dimen.padding_16))
                )
                Text(
                    text = "${stringResource(id = R.string.time_zone)} $timeZone",
                    style = MaterialTheme.typography.subtitle1
                )
            }
            Spacer(
                modifier = Modifier.height(dimensionResource(id = R.dimen.padding_8))
            )
            //Location by coordinates
            mainScreenState.address?.let { address ->
                Text(
                    text = "${stringResource(id = R.string.address)} $address",
                    style = MaterialTheme.typography.subtitle1
                )
            }
            Spacer(
                modifier = Modifier.height(dimensionResource(id = R.dimen.padding_16))
            )
            //List of weather description
            val weatherDescription = mainScreenState.currentWeather?.current?.weather?.map {
                "${it.description} "
            }
            weatherDescription?.joinToString(",")?.let { weather ->
                Text(
                    text = "${stringResource(id = R.string.description)} $weather",
                    style = MaterialTheme.typography.subtitle1
                )
            }
            Spacer(
                modifier = Modifier.height(dimensionResource(id = R.dimen.padding_8))
            )
            //List of icons
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                mainScreenState.currentWeather?.current?.weather?.forEach { weather ->
                    item {
                        AsyncImage(
                            modifier = Modifier.size(
                                dimensionResource(id = R.dimen.padding_96)
                            ),
                            model = weather.icon,
                            contentDescription = null,
                        )
                    }
                }
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = dimensionResource(id = R.dimen.padding_32)
                    )
            )
            PrimaryTextField(
                label = stringResource(id = R.string.latitude),
                value = mainScreenState.lat ?: "",
                onValueChange = {
                    viewModel.updateLat(it)
                }
            )
            Spacer(
                modifier = Modifier.height(dimensionResource(id = R.dimen.padding_16))
            )
            PrimaryTextField(
                label = stringResource(id = R.string.longitude),
                value = mainScreenState.lon ?: "",
                onValueChange = {
                    viewModel.updateLon(it)
                }
            )
            Spacer(
                modifier = Modifier.height(dimensionResource(id = R.dimen.padding_32))
            )
            val message = stringResource(id = R.string.coordinates_needed)
            PrimaryButton(
                text = stringResource(id = R.string.update_weather),
                onClick = {
                    mainScreenState.lat?.let { lat ->
                        mainScreenState.lon?.let { lon ->
                            viewModel.fetchWeather(
                                lat.toDouble(), lon.toDouble()
                            )
                        }
                    } ?: run {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }
    if (mainScreenState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

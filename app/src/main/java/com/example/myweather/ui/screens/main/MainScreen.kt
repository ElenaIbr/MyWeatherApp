package com.example.myweather.ui.screens.main

import android.content.Context
import android.location.Address
import android.location.Geocoder
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.myweather.R
import com.example.myweather.ui.screens.main.components.PrimaryButton
import com.example.myweather.ui.screens.main.components.PrimaryTextField
import com.example.myweather.ui.screens.main.viewmodel.MainScreenViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.io.IOException
import java.util.Locale

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

    if (mainScreenState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
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
            // timezone
            mainScreenState.currentWeather?.timezone?.let { timeZone ->
                Text(
                    text = "${stringResource(id = R.string.time_zone)} $timeZone",
                    style = MaterialTheme.typography.subtitle1
                )
            }
            Spacer(
                modifier = Modifier.height(dimensionResource(id = R.dimen.padding_8))
            )
            //list of weather description
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
            //Location by coordinates
            mainScreenState.currentWeather?.lat?.let { lat ->
                mainScreenState.currentWeather?.lon?.let { lon ->
                    getAddress(lat, lon, context)?.let { address ->
                        Text(
                            text = "${stringResource(id = R.string.address)} $address",
                            style = MaterialTheme.typography.subtitle1
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier.height(dimensionResource(id = R.dimen.padding_16))
            )
            //list og icons
            mainScreenState.currentWeather?.current?.weather?.forEach { weather ->
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
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
            PrimaryButton(
                text = stringResource(id = R.string.update_weather),
                onClick = {
                    mainScreenState.lat?.let { lat ->
                        mainScreenState.lon?.let { lon ->
                            viewModel.fetchWeather(
                                lat, lon
                            )
                        }
                    }
                }
            )
        }
    }
}

private fun getAddress(lat: Double, lng: Double, context: Context): String? {
    val geocoder = Geocoder(context, Locale.getDefault())
    return try {
        val addresses: List<Address>? = geocoder.getFromLocation(lat, lng, 1)
        val obj: Address = addresses!![0]
        "${obj.thoroughfare}, ${obj.postalCode} ${obj.subAdminArea}, ${obj.countryName}"
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

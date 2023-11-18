package com.example.myweather.ui.screens.main.components

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.myweather.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun PermissionsNotGranted() {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.White,
            darkIcons = true
        )
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.permissions_not_granted),
            style = MaterialTheme.typography.h5,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(
            modifier = Modifier.height(dimensionResource(id = R.dimen.permissions_denied_screen_spacer))
        )
        PrimaryButton(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.permissions_denied_button_padding)),
            text = stringResource(id = R.string.settings).uppercase(),
            buttonState = ButtonState.DEFAULT,
            onClick = {
                context.startActivity(
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                )
            }
        )
    }
}

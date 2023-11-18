package com.example.myweather.ui.screens.main.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.example.myweather.R

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String = "",
    buttonState: ButtonState? = ButtonState.DEFAULT,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(
                dimensionResource(id = R.dimen.primary_button_height)
            ),
        onClick = onClick,
        elevation = ButtonDefaults.elevation(
            defaultElevation = dimensionResource(id = R.dimen.primary_button_elevation),
            pressedElevation = dimensionResource(id = R.dimen.primary_button_elevation),
            disabledElevation = dimensionResource(id = R.dimen.primary_button_elevation),
        ),
        enabled = buttonState == ButtonState.DEFAULT,
        shape = RoundedCornerShape(
            dimensionResource(id = R.dimen.primary_button_corner)
        )
    ) {
        when (buttonState) {
            ButtonState.DEFAULT, ButtonState.DISABLED, null -> {
                Text(
                    text = text,
                    fontWeight = FontWeight(600),
                    lineHeight = TextUnit(24f, TextUnitType.Sp),
                    fontSize = TextUnit(18f, TextUnitType.Sp),
                    letterSpacing = TextUnit(0.3f, TextUnitType.Sp),
                )
            }
        }
    }
}

enum class ButtonState {
    DEFAULT,
    DISABLED
}

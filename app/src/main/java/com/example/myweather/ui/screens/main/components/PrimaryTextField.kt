package com.example.myweather.ui.screens.main.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.myweather.R

@Composable
fun PrimaryTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(),
        text = label,
        style = MaterialTheme.typography.subtitle1
    )
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(dimensionResource(id = R.dimen.input_field_corner))
            )
            .border(
                BorderStroke(
                    width = dimensionResource(id = R.dimen.input_field_border),
                    color = Color.LightGray
                ),
                RoundedCornerShape(dimensionResource(id = R.dimen.input_field_corner))
            ),
        value = value,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        ),
        textStyle = MaterialTheme.typography.body1,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.input_field_corner)),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        onValueChange = {
            onValueChange(it)
        }
    )
}

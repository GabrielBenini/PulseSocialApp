package com.example.pulsesocial.feature.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SignUpTextField(
    modifier: Modifier = Modifier,
    value: String,
    leadingIcon: @Composable (() -> Unit),
    onValueChange: (String) -> Unit,
    label: @Composable (() -> Unit),
) {

    OutlinedTextField(
        modifier = modifier,
        value = value,
        leadingIcon = leadingIcon,
        onValueChange = onValueChange,
        label = label
    )

}
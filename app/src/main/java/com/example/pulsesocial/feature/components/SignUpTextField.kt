package com.example.pulsesocial.feature.components

import android.R
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SignUpTextField(
    modifier: Modifier = Modifier,
    value: String,
    leadingIcon: @Composable (() -> Unit),
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    label: @Composable (() -> Unit),
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions
) {

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        singleLine = true,
        value = value,
        leadingIcon = leadingIcon,
        onValueChange = onValueChange,
        label = label,
        trailingIcon = trailingIcon,
        isError = errorMessage != null,
        supportingText = {
            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
    )

}
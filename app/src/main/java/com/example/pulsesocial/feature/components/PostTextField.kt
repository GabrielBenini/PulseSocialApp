package com.example.pulsesocial.feature.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pulsesocial.ui.theme.PulseSocialTheme

@Composable
fun PostTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable (() -> Unit),
    keyboardOptions: KeyboardOptions,

){

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = label,
        colors = OutlinedTextFieldDefaults
            .colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)
        ),
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(16.dp),

    )
}

@Preview(showBackground = true)
@Composable
fun PostTextFieldPreview(){
    PulseSocialTheme {
        PostTextField(
            value = "",
            onValueChange = { },
            label = {},
            keyboardOptions = KeyboardOptions(),
        )
    }
}

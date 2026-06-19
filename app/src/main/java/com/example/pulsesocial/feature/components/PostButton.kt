package com.example.pulsesocial.feature.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pulsesocial.ui.theme.PulseSocialTheme

@Composable
fun PostButton(
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f))
        ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(onClick = {onCameraClick()})
            ) {

                Icon(
                    imageVector = Icons.Outlined.PhotoCamera,
                    contentDescription = "Take Picture",
                    modifier = Modifier.padding(end = 16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Column() {

                    Text(
                        "Tirar foto",
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        "Abra a câmera para tirar uma foto",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = "Arrow",
                    modifier = Modifier
                        .size(16.dp),

                )
            }

            Divider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(onClick = { onGalleryClick()})
            ) {

                Icon(
                    imageVector = Icons.Outlined.Image,
                    contentDescription = "Take Picture",
                    modifier = Modifier.padding(end = 16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Column() {

                    Text(
                        "Escolher da galeria",
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        "Selecione uma foto ou video da galeria",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = "Arrow",
                    modifier = Modifier
                        .size(16.dp),

                    )
            }

        }

    }
}


@Preview(showBackground = true)
@Composable
fun PostButtonPreview() {
    PulseSocialTheme {
        PostButton(
            onCameraClick = {},
            onGalleryClick = {}
        )
    }
}
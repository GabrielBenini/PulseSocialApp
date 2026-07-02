package com.example.pulsesocial.feature.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pulsesocial.feature.navigation.AppNavigation
import com.example.pulsesocial.feature.settings.SettingsViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    onSettingsClick: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Profile screen")

        IconButton(
            onClick = { onSettingsClick() }
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings Button"
            )
        }
    }
}
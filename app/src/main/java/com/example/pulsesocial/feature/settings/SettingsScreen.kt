package com.example.pulsesocial.feature.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pulsesocial.R
import com.example.pulsesocial.feature.navigation.AppNavigation
import com.example.pulsesocial.feature.settings.SettingsContract.Event.OnLogoutClick

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    navController: NavController
){

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                SettingsContract.Effect.NavigateToLogin -> {
                    navController.navigate(AppNavigation.LoginScreen.route){
                        popUpTo(0)
                    }
                }
            }
        }
    }

    ScreenContent(
        onLogoutClicked = { viewModel.handleEvent(OnLogoutClick) }
    )

}

@Composable
fun ScreenContent(
    onLogoutClicked: () -> Unit
){

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(R.drawable.app_feed_background),
            contentDescription = "Feed Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = { onLogoutClicked() }
            ) {
                Text("Logout")
            }
        }
    }
}

@Preview
@Composable
fun ScreenContentPreview(){
    ScreenContent(
        onLogoutClicked = {}
    )
}

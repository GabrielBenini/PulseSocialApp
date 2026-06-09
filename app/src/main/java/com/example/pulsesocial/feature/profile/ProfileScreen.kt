package com.example.pulsesocial.feature.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pulsesocial.data.session.SessionManager
import com.example.pulsesocial.feature.navigation.AppNavigation

@Composable
fun ProfileScreen(
    navController: NavController,
    viewmodel: ProfileViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewmodel.uiEffect.collect { effect ->
            when(effect) {
                ProfileContract.Effect.NavigateToLogin -> {
                    navController.navigate(AppNavigation.LoginScreen.route){
                        popUpTo(0)
                    }
                }

                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Profile screen")

        Button(
            onClick = {viewmodel.logout()}
        ) {
            Text("Logout")
        }
    }
}
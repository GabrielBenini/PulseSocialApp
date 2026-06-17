package com.example.pulsesocial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.navArgument
import com.example.pulsesocial.feature.feed.FeedScreen
import com.example.pulsesocial.feature.main.MainScreen
import com.example.pulsesocial.feature.navigation.NavGraph
import com.example.pulsesocial.ui.theme.PulseSocialTheme
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PulseSocialTheme {
                MainScreen()
            }
        }
    }
}

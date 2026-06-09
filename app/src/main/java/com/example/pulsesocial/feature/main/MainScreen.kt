package com.example.pulsesocial.feature.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AppBarRow
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pulsesocial.feature.navigation.AppNavigation
import com.example.pulsesocial.feature.navigation.NavGraph

@Composable
fun MainScreen() {

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val bottomBarRouters = listOf(
        AppNavigation.FeedScreen.route,
        AppNavigation.ProfileScreen.route,
        AppNavigation.PostScreen.route
    )



    NavGraph(
        navController = navController
    )

    Scaffold(

        bottomBar = {
            if (currentRoute in bottomBarRouters) {

                BottomAppBar(
                    modifier = Modifier.height(80.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                navController.navigate(AppNavigation.FeedScreen.route)
                            }
                        ) {
                            Icon(Icons.Default.Home, contentDescription = "Home")
                        }

                        IconButton(
                            onClick = {}
                        ) {
                            Icon(Icons.Default.Search, contentDescription = "Descobrir")
                        }

                        IconButton(
                            onClick = {
                                navController.navigate(AppNavigation.PostScreen.route)
                            }
                        ) {
                            Icon(Icons.Default.AddCircleOutline, contentDescription = "Create Post")
                        }

                        IconButton(
                            onClick = {}
                        ) {
                            Icon(Icons.Default.NotificationsNone, contentDescription = "Notifications")
                        }

                        IconButton(
                            onClick = {
                                navController.navigate(AppNavigation.ProfileScreen.route)
                            }
                        ) {
                            Icon(Icons.Default.PersonOutline, contentDescription = "Profile")
                        }
                    }
                }
            }
        }

    ) { padding ->

        NavGraph(
            navController = navController,
            modifier = Modifier.padding(padding)
        )
    }
}
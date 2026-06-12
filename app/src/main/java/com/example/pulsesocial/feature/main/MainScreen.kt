package com.example.pulsesocial.feature.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pulsesocial.feature.feed.FeedContract
import com.example.pulsesocial.feature.feed.FeedViewModel
import com.example.pulsesocial.feature.navigation.AppNavigation
import com.example.pulsesocial.feature.navigation.NavGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    feedViewModel: FeedViewModel = hiltViewModel()
) {

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val bottomBarRouters = listOf(
        AppNavigation.FeedScreen.route,
        AppNavigation.ProfileScreen.route,
        AppNavigation.PostScreen.route
    )

    LaunchedEffect(Unit) {
        feedViewModel.uiEffect.collect { effect ->
            when (effect) {

                is FeedContract.Effect.ShowSuccess -> {
                    feedViewModel.handleEvent(FeedContract.Event.OnRefresh)
                }

                is FeedContract.Effect.ShowError -> {
                    // futuramente snackbar
                }
            }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Pulse",
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
            )
        },
        bottomBar = {
            if (currentRoute in bottomBarRouters) {

                BottomAppBar(
                    modifier = Modifier
                        .height(80.dp),
                    containerColor = MaterialTheme.colorScheme.surface

                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            enabled = currentRoute != AppNavigation.FeedScreen.route,
                            onClick = {
                                navController.navigate(AppNavigation.FeedScreen.route)
                            }
                        ) {
                            if (currentRoute == AppNavigation.FeedScreen.route){
                                Icon(
                                    Icons.Filled.Home,
                                    contentDescription = "Home Filled",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            } else {
                                Icon(
                                    Icons.Outlined.Home,
                                    contentDescription = "Home Outlined",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Descobrir",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        IconButton(
                            enabled = currentRoute != AppNavigation.PostScreen.route,
                            onClick = {
                                navController.navigate(AppNavigation.PostScreen.route)
                            }
                        ) {
                            Icon(
                                Icons.Default.AddCircleOutline,
                                contentDescription = "Create Post",
                                tint = if (currentRoute == AppNavigation.PostScreen.route) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                }
                            )
                        }

                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                Icons.Default.NotificationsNone,
                                contentDescription = "Notifications",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        IconButton(
                            enabled = currentRoute != AppNavigation.ProfileScreen.route,
                            onClick = {
                                navController.navigate(AppNavigation.ProfileScreen.route)
                            }
                        ) {
                            if (currentRoute == AppNavigation.ProfileScreen.route){
                                Icon(
                                    Icons.Filled.Person,
                                    contentDescription = "Home Filled",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            } else {
                                Icon(
                                    Icons.Outlined.Person,
                                    contentDescription = "Home Outlined",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
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
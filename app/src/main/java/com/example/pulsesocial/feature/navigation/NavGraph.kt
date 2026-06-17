package com.example.pulsesocial.feature.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pulsesocial.data.session.SessionManager
import com.example.pulsesocial.feature.comments.CommentsScreen
import com.example.pulsesocial.feature.feed.FeedScreen
import com.example.pulsesocial.feature.login.LoginScreen
import com.example.pulsesocial.feature.post.PostScreen
import com.example.pulsesocial.feature.profile.ProfileScreen
import com.example.pulsesocial.feature.signup.SignUpScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AppStartViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _startDestination = MutableStateFlow<String?>(null)
    val startDestination = _startDestination.asStateFlow()

    init {
        viewModelScope.launch {
            val userId = sessionManager.getUserId()
            _startDestination.value =
                if (userId != null) AppNavigation.FeedScreen.route
                else AppNavigation.LoginScreen.route
        }
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AppStartViewModel = hiltViewModel()
) {

    val startDestination by viewModel.startDestination.collectAsState()

    if (startDestination == null) return

    NavHost(
        navController = navController,
        startDestination = startDestination!!,
        modifier = modifier
    ) {

        composable(AppNavigation.LoginScreen.route) {
            LoginScreen(
                onSignUpClick = {
                    navController.navigate(AppNavigation.SignUpScreen.route){
                        popUpTo(AppNavigation.LoginScreen.route) { inclusive = true }
                    }
                },
                onLoginClicked = {
                    navController.navigate(AppNavigation.FeedScreen.route) {
                        popUpTo(AppNavigation.LoginScreen.route) { inclusive = true }
                    }
                }
            )
        }

        composable(AppNavigation.FeedScreen.route) {
            FeedScreen(navController)
        }

        composable(AppNavigation.PostScreen.route) {
            PostScreen(navController)
        }

        composable(AppNavigation.ProfileScreen.route) {
            ProfileScreen(navController)
        }

        composable(AppNavigation.SignUpScreen.route) {
            SignUpScreen(
                onNavigateToLogin = {
                    navController.navigate(AppNavigation.LoginScreen.route) {
                        popUpTo(AppNavigation.SignUpScreen.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = AppNavigation.CommentsWithArgs.route,
            arguments = listOf(
                navArgument("postId") { type = NavType.LongType }
            )
        ) { backStackEntry ->

            val postId = backStackEntry.arguments?.getLong("postId") ?: 0
            CommentsScreen(postId = postId)
        }
    }
}

sealed class AppNavigation(val route: String) {
    object SignUpScreen : AppNavigation("signup")
    object ProfileScreen : AppNavigation("profile")
    object LoginScreen : AppNavigation("login")
    object FeedScreen : AppNavigation("feed")
    object CommentsScreen : AppNavigation("comments")
    object PostScreen : AppNavigation("post")
    object CommentsWithArgs : AppNavigation("comments/{postId}")
}
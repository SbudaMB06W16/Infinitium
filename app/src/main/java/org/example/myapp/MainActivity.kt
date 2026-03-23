package org.example.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.example.myapp.ui.theme.InfinitiumTheme

@Serializable object Welcome
@Serializable object Main
@Serializable object Tutorial
@Serializable data class TopicDetailRoute(val topicTitle: String)
@Serializable object Profile
@Serializable object About
@Serializable object Leaderboard
@Serializable object Challenge
@Serializable object Settings

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InfinitiumTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Welcome) {
        composable<Welcome> {
            WelcomeScreen(onNavigateToMain = {
                navController.navigate(Main) {
                    popUpTo(Welcome) { inclusive = true }
                }
            })
        }

        val navigateToMain: () -> Unit = {
            navController.navigate(Main) {
                popUpTo(Main) { inclusive = true }
            }
        }
        val navigateToProfile: () -> Unit = { navController.navigate(Profile) }
        val navigateToAbout: () -> Unit = { navController.navigate(About) }
        val navigateToLeaderboard: () -> Unit = { navController.navigate(Leaderboard) }
        val navigateToChallenge: () -> Unit = { navController.navigate(Challenge) }
        
        composable<Main> {
            MainScreen(
                onTopicClick = { topicTitle ->
                    when (topicTitle) {
                        "Tutorial" -> navController.navigate(Tutorial)
                        else -> navController.navigate(TopicDetailRoute(topicTitle))
                    }
                },
                onNavigateToMain = navigateToMain,
                onNavigateToProfile = navigateToProfile,
                onNavigateToAbout = navigateToAbout,
                onNavigateToLeaderboard = navigateToLeaderboard,
                onNavigateToChallenge = navigateToChallenge
            )
        }
        
        // Decoupled Tutorial Screen
        tutorialScreen(onNavigateUp = { navController.navigateUp() })

        composable<TopicDetailRoute> { backStackEntry ->
            val route: TopicDetailRoute = backStackEntry.toRoute()
            TopicDetailScreen(
                topicTitle = route.topicTitle,
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable<Profile> { 
            ProfileScreen(
                onNavigateToMain = navigateToMain,
                onNavigateToAbout = navigateToAbout,
                onNavigateToLeaderboard = navigateToLeaderboard,
                onNavigateToChallenge = navigateToChallenge,
                onSettingsClicked = { navController.navigate(Settings) }
            )
        }
        composable<About> { 
            AboutScreen(
                onNavigateToMain = navigateToMain,
                onNavigateToProfile = navigateToProfile,
                onNavigateToLeaderboard = navigateToLeaderboard,
                onNavigateToChallenge = navigateToChallenge
            )
        }
        composable<Leaderboard> { 
            LeaderboardScreen(
                onNavigateToMain = navigateToMain,
                onNavigateToProfile = navigateToProfile,
                onNavigateToAbout = navigateToAbout,
                onNavigateToChallenge = navigateToChallenge
            )
        }
        composable<Challenge> { 
            ChallengeScreen(
                onNavigateToMain = navigateToMain,
                onNavigateToProfile = navigateToProfile,
                onNavigateToAbout = navigateToAbout,
                onNavigateToLeaderboard = navigateToLeaderboard
            )
        }
        composable<Settings> {
            SettingsScreen(
                onNavigateToMain = navigateToMain,
                onNavigateToProfile = navigateToProfile,
                onNavigateToAbout = navigateToAbout,
                onNavigateToLeaderboard = navigateToLeaderboard,
                onNavigateToChallenge = navigateToChallenge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppNavHostPreview() {
    InfinitiumTheme {
        val navController = rememberNavController()
        AppNavHost(navController = navController)
    }
}

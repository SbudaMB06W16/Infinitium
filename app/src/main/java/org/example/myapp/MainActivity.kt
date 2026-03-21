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
import org.example.myapp.ui.theme.InfinitiumTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InfinitiumTheme {
                // A surface container using the 'background' color from the theme
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
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(onNavigateToMain = {
                navController.navigate("main") {
                    popUpTo("welcome") { inclusive = true }
                }
            })
        }
        val mainScreenRoute = "main"
        val navigateToMain: () -> Unit = {
            navController.navigate(mainScreenRoute) {
                popUpTo(mainScreenRoute) { inclusive = true }
            }
        }
        val navigateToProfile: () -> Unit = { navController.navigate("profile") }
        val navigateToAbout: () -> Unit = { navController.navigate("about") }
        val navigateToLeaderboard: () -> Unit = { navController.navigate("leaderboard") }
        val navigateToChallenge: () -> Unit = { navController.navigate("challenge") }
        
        composable(mainScreenRoute) {
            MainScreen(
                onTopicClick = { topicTitle ->
                    when (topicTitle) {
                        "Tutorial" -> navController.navigate("tutorial")
                        else -> navController.navigate("topicDetail/$topicTitle")
                    }
                },
                onNavigateToMain = navigateToMain,
                onNavigateToProfile = navigateToProfile,
                onNavigateToAbout = navigateToAbout,
                onNavigateToLeaderboard = navigateToLeaderboard,
                onNavigateToChallenge = navigateToChallenge
            )
        }
        composable("tutorial") {
            TutorialScreen(onNavigateUp = { navController.navigateUp() })
        }
        composable("topicDetail/{topicTitle}") { backStackEntry ->
            TopicDetailScreen(
                topicTitle = backStackEntry.arguments?.getString("topicTitle") ?: "",
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable("profile") { 
            ProfileScreen(
                onNavigateToMain = navigateToMain,
                onNavigateToAbout = navigateToAbout,
                onNavigateToLeaderboard = navigateToLeaderboard,
                onNavigateToChallenge = navigateToChallenge,
                onSettingsClicked = { navController.navigate("settings") }
            )
        }
        composable("about") { 
            AboutScreen(
                onNavigateToMain = navigateToMain,
                onNavigateToProfile = navigateToProfile,
                onNavigateToLeaderboard = navigateToLeaderboard,
                onNavigateToChallenge = navigateToChallenge
            )
        }
        composable("leaderboard") { 
            LeaderboardScreen(
                onNavigateToMain = navigateToMain,
                onNavigateToProfile = navigateToProfile,
                onNavigateToAbout = navigateToAbout,
                onNavigateToChallenge = navigateToChallenge
            )
        }
        composable("challenge") { 
            ChallengeScreen(
                onNavigateToMain = navigateToMain,
                onNavigateToProfile = navigateToProfile,
                onNavigateToAbout = navigateToAbout,
                onNavigateToLeaderboard = navigateToLeaderboard
            )
        }
        composable("settings") {
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

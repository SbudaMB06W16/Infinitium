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
        composable(mainScreenRoute) {
            MainScreen(
                onTopicClick = { topicTitle ->
                    if (topicTitle == "Algebraic Expressions") {
                        navController.navigate("algebraicExpressions")
                    } else {
                        navController.navigate("topicDetail/$topicTitle")
                    }
                },
                onNavigateToMain = navigateToMain,
                onNavigateToProfile = { navController.navigate("profile") },
                onNavigateToAbout = { navController.navigate("about") },
                onNavigateToLeaderboard = { navController.navigate("leaderboard") },
                onNavigateToChallenge = { navController.navigate("challenge") }
            )
        }
        composable("topicDetail/{topicTitle}") { backStackEntry ->
            TopicDetailScreen(
                topicTitle = backStackEntry.arguments?.getString("topicTitle") ?: "",
                onNavigateUp = { navController.navigateUp() },
                onNavigateToMain = navigateToMain,
                onNavigateToProfile = { navController.navigate("profile") },
                onNavigateToAbout = { navController.navigate("about") },
                onNavigateToLeaderboard = { navController.navigate("leaderboard") },
                onNavigateToChallenge = { navController.navigate("challenge") }
            )
        }
        composable("profile") { 
            ProfileScreen(
                onNavigateToMain = navigateToMain,
                onNavigateToAbout = { navController.navigate("about") },
                onNavigateToLeaderboard = { navController.navigate("leaderboard") },
                onNavigateToChallenge = { navController.navigate("challenge") },
                onSettingsClicked = { navController.navigate("settings") }
            )
        }
        composable("about") { 
            AboutScreen(
                onNavigateToMain = navigateToMain,
                onNavigateToProfile = { navController.navigate("profile") },
                onNavigateToLeaderboard = { navController.navigate("leaderboard") },
                onNavigateToChallenge = { navController.navigate("challenge") }
            )
        }
        composable("leaderboard") { 
            LeaderboardScreen(
                onNavigateToMain = navigateToMain,
                onNavigateToProfile = { navController.navigate("profile") },
                onNavigateToAbout = { navController.navigate("about") },
                onNavigateToChallenge = { navController.navigate("challenge") }
            )
        }
        composable("challenge") { 
            ChallengeScreen(
                onNavigateToMain = navigateToMain,
                onNavigateToProfile = { navController.navigate("profile") },
                onNavigateToAbout = { navController.navigate("about") },
                onNavigateToLeaderboard = { navController.navigate("leaderboard") }
            )
        }
        composable("settings") {
            SettingsScreen(
                onNavigateToMain = navigateToMain,
                onNavigateToProfile = { navController.navigate("profile") },
                onNavigateToAbout = { navController.navigate("about") },
                onNavigateToLeaderboard = { navController.navigate("leaderboard") },
                onNavigateToChallenge = { navController.navigate("challenge") }
            )
        }
        composable("algebraicExpressions") {
            TopicDetailScreen(
                topicTitle = "Algebraic Expressions",
                onNavigateUp = { navController.navigateUp() },
                onNavigateToMain = navigateToMain,
                onNavigateToProfile = { navController.navigate("profile") },
                onNavigateToAbout = { navController.navigate("about") },
                onNavigateToLeaderboard = { navController.navigate("leaderboard") },
                onNavigateToChallenge = { navController.navigate("challenge") }
            )
        }
    }
}

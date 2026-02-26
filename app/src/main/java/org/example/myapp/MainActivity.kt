package org.example.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
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
                    NavHost(navController = navController, startDestination = "welcome") {
                        composable("welcome") {
                            WelcomeScreen(onNavigateToMain = { 
                                navController.navigate("main") {
                                    popUpTo("welcome") { inclusive = true }
                                }
                            })
                        }
                        composable("main") {
                            MainScreen(onTopicClick = { topicTitle ->
                                navController.navigate("topicDetail/$topicTitle")
                            })
                        }
                        composable("topicDetail/{topicTitle}") { backStackEntry ->
                            TopicDetailScreen(
                                topicTitle = backStackEntry.arguments?.getString("topicTitle") ?: "",
                                onNavigateUp = { navController.navigateUp() }
                            )
                        }
                    }
                }
            }
        }
    }
}

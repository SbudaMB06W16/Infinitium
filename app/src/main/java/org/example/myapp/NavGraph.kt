package org.example.myapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(onTopicClick = { topicTitle ->
                navController.navigate("topicDetail/$topicTitle")
            })
        }
        composable(
            route = "topicDetail/{topicTitle}",
            arguments = listOf(navArgument("topicTitle") { defaultValue = "" })
        ) { backStackEntry ->
            TopicDetailScreen(
                topicTitle = backStackEntry.arguments?.getString("topicTitle") ?: "",
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}

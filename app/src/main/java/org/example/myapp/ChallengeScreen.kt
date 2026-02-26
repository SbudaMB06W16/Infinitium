package org.example.myapp

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeScreen(
    onNavigateToMain: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onNavigateToLeaderboard: () -> Unit
) {
    BackHandler { onNavigateToMain() }
    Scaffold(
        floatingActionButton = {
            FloatingMenu(
                currentScreen = "challenge",
                onNavigateToMain = onNavigateToMain,
                onNavigateToProfile = onNavigateToProfile,
                onNavigateToAbout = onNavigateToAbout,
                onNavigateToLeaderboard = onNavigateToLeaderboard,
                onNavigateToChallenge = {}
            )
        }
    ) {
        innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
            Text("Challenge Screen")
        }
    }
}

package org.example.myapp

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FloatingMenu(
    currentScreen: String,
    onNavigateToMain: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onNavigateToLeaderboard: () -> Unit,
    onNavigateToChallenge: () -> Unit
) {
    var isMenuOpen by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(if (isMenuOpen) 45f else 0f)
    val vibrantBlue = Color(0xFF0088FF)

    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier.padding(bottom = 14.dp, end = 5.dp) // Adjusted padding
    ) {
        if (isMenuOpen) {
            if (currentScreen != "main") {
                FloatingActionButton(
                    onClick = onNavigateToMain,
                    modifier = Modifier.padding(bottom = 16.dp),
                    shape = CircleShape,
                    containerColor = vibrantBlue
                ) {
                    Icon(Icons.Default.MenuBook, contentDescription = "Learning", tint = Color.White)
                }
            }
            if (currentScreen != "profile") {
                FloatingActionButton(
                    onClick = onNavigateToProfile,
                    modifier = Modifier.padding(bottom = 16.dp),
                    shape = CircleShape,
                    containerColor = vibrantBlue
                ) {
                    Icon(Icons.Default.AccountCircle, contentDescription = "Profile", tint = Color.White)
                }
            }
            if (currentScreen != "about") {
                FloatingActionButton(
                    onClick = onNavigateToAbout,
                    modifier = Modifier.padding(bottom = 16.dp),
                    shape = CircleShape,
                    containerColor = vibrantBlue
                ) {
                    Icon(Icons.Default.Info, contentDescription = "About", tint = Color.White)
                }
            }
            if (currentScreen != "leaderboard") {
                FloatingActionButton(
                    onClick = onNavigateToLeaderboard,
                    modifier = Modifier.padding(bottom = 16.dp),
                    shape = CircleShape,
                    containerColor = vibrantBlue
                ) {
                    Icon(Icons.Default.Leaderboard, contentDescription = "Leaderboard", tint = Color.White)
                }
            }
            if (currentScreen != "challenge") {
                FloatingActionButton(
                    onClick = onNavigateToChallenge,
                    modifier = Modifier.padding(bottom = 16.dp),
                    shape = CircleShape,
                    containerColor = vibrantBlue
                ) {
                    Icon(Icons.Default.MilitaryTech, contentDescription = "Challenge", tint = Color.White)
                }
            }
        }
        FloatingActionButton(
            onClick = { isMenuOpen = !isMenuOpen },
            shape = CircleShape,
            modifier = Modifier.rotate(rotationAngle),
            containerColor = vibrantBlue
        ) {
            Icon(if (isMenuOpen) Icons.Default.Close else Icons.Default.Add, contentDescription = "Menu", tint = Color.White)
        }
    }
}

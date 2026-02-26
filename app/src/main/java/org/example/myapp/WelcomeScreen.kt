package org.example.myapp

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun GridBackground(modifier: Modifier = Modifier, color: Color) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val gridSize = 40.dp.toPx()
        val strokeWidth = 1.dp.toPx()
        val lineCountX = (size.width / gridSize).toInt() + 1
        val lineCountY = (size.height / gridSize).toInt() + 1

        for (i in 0..lineCountX) {
            val startX = i * gridSize
            drawLine(
                color = color,
                start = Offset(startX, 0f),
                end = Offset(startX, size.height),
                strokeWidth = strokeWidth
            )
        }

        for (i in 0..lineCountY) {
            val startY = i * gridSize
            drawLine(
                color = color,
                start = Offset(0f, startY),
                end = Offset(size.width, startY),
                strokeWidth = strokeWidth
            )
        }
    }
}

@Composable
fun WelcomeScreen(onNavigateToMain: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        GridBackground(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to Infinitium",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
            Text(
                text = "The Land of Mathematics",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(64.dp))
            Button(onClick = onNavigateToMain) {
                Text("Sign In")
            }
            Button(onClick = onNavigateToMain) {
                Text("Sign Up")
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = onNavigateToMain) {
                Text("Skip")
            }
        }
    }
}

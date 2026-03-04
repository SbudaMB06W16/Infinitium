package org.example.myapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.myapp.ui.theme.GreatVibes

@Composable
fun AlgebraicExpressionsScreen() {
    var isPlaying by remember { mutableStateOf(false) }
    val labels = listOf("1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title centered
        Text(
            text = "Algebraic Expressions",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            textAlign = TextAlign.Center
        )

        // Light grey card with rounded corners - size depends on content
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE0E0E0) // Light grey background
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 2.dp,
                        top = 1.dp,
                        end = 1.dp,
                        bottom = 16.dp
                    )
            ) {
                // Header section
                Box(modifier = Modifier.fillMaxWidth()) {
                    // "Exercise 1" in script font (GreatVibes)
                    Text(
                        text = "Exercise 1",
                        fontFamily = GreatVibes,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )

                    // Action icons at the top right corner
                    Row(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        horizontalArrangement = Arrangement.spacedBy(0.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Undo icon
                        Icon(
                            imageVector = Icons.Default.Undo,
                            contentDescription = "Undo",
                            modifier = Modifier
                                .size(32.dp)
                                .clickable { /* Add undo logic here */ }
                        )

                        // Play/Pause icon
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "Pause" else "Play",
                            modifier = Modifier
                                .size(32.dp)
                                .clickable { isPlaying = !isPlaying }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // sub-cards labeled 1-9 arranged side by side
                Row(
                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    labels.forEach { label ->
                        Card(
                            modifier = Modifier.size(35.dp), // Increased slightly to fit "4th", etc.
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.bodyMedium, // Adjusted style for better fit
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


package org.example.myapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinFlipScreen() {
    var score by remember { mutableStateOf(0) }
    val guesses = remember { mutableStateOf(List(5) { "" }) }
    val results = remember { mutableStateOf(List(5) { "" }) }
    var gameStarted by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Your Guesses (H/T)")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (i in 0 until 5) {
                TextField(
                    value = guesses.value[i],
                    onValueChange = { newGuess ->
                        val newGuesses = guesses.value.toMutableList()
                        newGuesses[i] = newGuess
                        guesses.value = newGuesses
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Results")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (i in 0 until 5) {
                Text(results.value[i], modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = {
                gameStarted = true
                val newResults = mutableListOf<String>()
                var newScore = 0
                for (i in 0 until 5) {
                    val result = if (Random.nextBoolean()) "H" else "T"
                    newResults.add(result)
                    if (guesses.value[i].equals(result, ignoreCase = true)) {
                        newScore++
                    }
                }
                results.value = newResults
                score = newScore
            }) {
                Text("Flip Coins")
            }

            Button(onClick = {
                gameStarted = false
                guesses.value = List(5) { "" }
                results.value = List(5) { "" }
                score = 0
            }) {
                Text("New Game")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (gameStarted) {
            Text("Score: $score")
        }
    }
}

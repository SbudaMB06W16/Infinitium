package org.example.myapp

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yourpackage.engine.Equation
import com.yourpackage.engine.Expression

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicDetailScreen(
    topicTitle: String,
    onNavigateUp: () -> Unit,
    onNavigateToMain: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onNavigateToLeaderboard: () -> Unit,
    onNavigateToChallenge: () -> Unit
) {
    BackHandler { onNavigateUp() }

    Scaffold(
        floatingActionButton = {
            FloatingMenu(
                currentScreen = "topicDetail",
                onNavigateToMain = onNavigateToMain,
                onNavigateToProfile = onNavigateToProfile,
                onNavigateToAbout = onNavigateToAbout,
                onNavigateToLeaderboard = onNavigateToLeaderboard,
                onNavigateToChallenge = onNavigateToChallenge
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = topicTitle,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(start = 4.dp, bottom = 24.dp)
            )

            if (topicTitle == "Algebraic Expressions") {
                AlgebraicSolver()
            } else {
                Text("Details for $topicTitle will be shown here.")
            }
        }
    }
}

@Composable
fun AlgebraicSolver() {
    val steps = remember {
        val initialEquation = Equation(Expression(1, 3), Expression(0, 0))
        val movedEquation = Equation(Expression(1, 0), Expression(0, -3))
        val solved = "x = -3"
        listOf(initialEquation.toString(), movedEquation.toString(), solved)
    }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(steps) { step ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Text(
                    text = step,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(24.dp).align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

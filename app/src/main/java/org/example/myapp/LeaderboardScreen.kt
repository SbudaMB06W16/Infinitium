package org.example.myapp

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(
    onNavigateToMain: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onNavigateToChallenge: () -> Unit
) {
    // A larger, randomly generated list of users for a complete leaderboard feel
    val users = rememberLeaderboardUsers()

    BackHandler { onNavigateToMain() }

    Scaffold(
        floatingActionButton = {
            FloatingMenu(
                currentScreen = "leaderboard",
                onNavigateToMain = onNavigateToMain,
                onNavigateToProfile = onNavigateToProfile,
                onNavigateToAbout = onNavigateToAbout,
                onNavigateToLeaderboard = {},
                onNavigateToChallenge = onNavigateToChallenge
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Text(
                text = "Leaderboard",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(start = 4.dp, bottom = 24.dp)
            )

            // Display for the top-ranked user
            TopPlayerBanner(user = users.first())

            Spacer(modifier = Modifier.height(24.dp))

            // Card holding the rest of the leaderboard players
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                LazyColumn(modifier = Modifier.padding(vertical = 8.dp)) {
                    itemsIndexed(users.subList(1, users.size)) { index, user ->
                        PlayerRow(rank = index + 2, user = user)
                        if (index < users.size - 2) {
                            Divider(modifier = Modifier.padding(start = 72.dp, end = 16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TopPlayerBanner(user: User) {
    val vibrantBlue = Color(0xFF0088FF)
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            DefaultProfileImage(size = 100.dp, modifier = Modifier.border(4.dp, vibrantBlue, CircleShape))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "#1",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = vibrantBlue
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = user.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${user.score} Points",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun PlayerRow(rank: Int, user: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$rank",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(40.dp)
        )
        DefaultProfileImage(size = 48.dp)
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = user.name, 
            style = MaterialTheme.typography.bodyLarge, 
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "${user.score}", 
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun DefaultProfileImage(size: Dp, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(0.8f) // Scale icon within the black background
        )
    }
}

@Composable
private fun rememberLeaderboardUsers(): List<User> {
    return remember {
        val names = listOf(
            "Rihanna", "Sanjib", "Pankaj", "Nur", "Manas", "Liam", "Olivia", "Noah", "Emma", "Oliver",
            "Ava", "Elijah", "Charlotte", "William", "Sophia", "James", "Amelia", "Benjamin", "Isabella", "Lucas"
        )
        names.mapIndexed { index, name ->
            User(name = name, score = 2500 - (index * 110) - (0..30).random())
        }
    }
}

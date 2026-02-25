package org.example.myapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Architecture
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.MultilineChart
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material.icons.filled.Superscript
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.myapp.ui.theme.InfinitiumTheme

// 1. DATA STRUCTURE: Represents a single topic
data class TopicItem(
    val icon: ImageVector,
    val title: String,
)

// 2. DATA: To add or remove items, just edit this list!
private val topicsData = listOf(
    // Topics Group
    listOf(
        TopicItem(Icons.Filled.Calculate, "Algebraic Expressions"),
        TopicItem(Icons.Filled.Superscript, "Exponents"),
        TopicItem(Icons.Filled.FormatListNumbered, "Number Patterns"),
        TopicItem(Icons.Filled.Equalizer, "Equations and Inequalities"),
        TopicItem(Icons.Filled.SquareFoot, "Trigonometry"),
        TopicItem(Icons.Filled.ShowChart, "Functions"),
        TopicItem(Icons.Filled.Architecture, "Euclidean Geometry"),
        TopicItem(Icons.Filled.Analytics, "Analytical Geometry"),
        TopicItem(Icons.Filled.TrendingUp, "Finance and Growth"),
        TopicItem(Icons.Filled.BarChart, "Statistics"),
        TopicItem(Icons.Filled.Casino, "Probability"),
        TopicItem(Icons.Filled.MultilineChart, "Calculus"),
    )
)

@Composable
fun TopicItemRow(item: TopicItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp), // Increased vertical padding
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.title,
            modifier = Modifier.size(28.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = item.title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun TopicsGroup(items: List<TopicItem>) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column {
            items.forEachIndexed { index, item ->
                TopicItemRow(item = item)
                if (index < items.size - 1) {
                    Divider(modifier = Modifier.padding(start = 56.dp, end = 16.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold {
        innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // Screen Title
            Text(
                text = "Infinitium",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(start = 20.dp, top = 24.dp)
            )
            Text(
                text = "Mathematics",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(start = 20.dp, bottom = 16.dp)
            )
            // The list of topic groups
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp) // Space between groups
            ) {
                items(topicsData) { group ->
                    TopicsGroup(items = group)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    InfinitiumTheme(darkTheme = true) {
        MainScreen()
    }
}

package org.example.myapp

import androidx.compose.runtime.Composable

@Composable
fun AlgebraicExpressionsScreen(
    onNavigateUp: () -> Unit
) {
    TopicDetailScreen(
        topicTitle = "Algebraic Expressions",
        onNavigateUp = onNavigateUp
    )
}

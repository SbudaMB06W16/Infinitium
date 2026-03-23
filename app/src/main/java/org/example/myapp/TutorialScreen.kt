package org.example.myapp

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.example.myapp.ui.theme.GreatVibes

/**
 * Extension function to decouple Tutorial navigation from MainActivity.
 */
fun NavGraphBuilder.tutorialScreen(onNavigateUp: () -> Unit) {
    composable<Tutorial> {
        TutorialScreen(onNavigateUp = onNavigateUp)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TutorialScreen(
    onNavigateUp: () -> Unit,
    viewModel: TutorialViewModel = viewModel()
) {
    BackHandler { onNavigateUp() }

    val items = viewModel.items
    val history = viewModel.history
    val isPlaying by viewModel.isPlaying.collectAsState()
    
    val historyScrollState = rememberScrollState()

    LaunchedEffect(history.size) {
        if (history.isNotEmpty()) {
            historyScrollState.animateScrollTo(historyScrollState.maxValue)
        }
    }

    DragContainer(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Tutorial",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                textAlign = TextAlign.Center
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    // Header
                    Box(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
                        Row(
                            modifier = Modifier.align(Alignment.CenterStart),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Exercise 1: ",
                                fontFamily = GreatVibes,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }

                        Row(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Undo, 
                                "Undo", 
                                Modifier
                                    .size(32.dp)
                                    .combinedClickable(
                                        enabled = history.isNotEmpty(),
                                        onClick = { viewModel.undo() },
                                        onLongClick = { viewModel.loadInitialState() }
                                    ),
                                tint = if (history.isNotEmpty()) MaterialTheme.colorScheme.primary else Color.Gray
                            )
                            Icon(
                                if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                if (isPlaying) "Pause" else "Play",
                                Modifier
                                    .size(32.dp)
                                    .clickable { viewModel.togglePlay() }
                            )
                        }
                    }

                    // History
                    if (history.isNotEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .heightIn(max = 120.dp)
                                .verticalScroll(historyScrollState),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            history.forEachIndexed { index, state ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Step ${index + 1}",
                                        fontSize = 12.sp,
                                        color = Color.Gray,
                                        modifier = Modifier.width(50.dp)
                                    )
                                    Row(
                                        modifier = Modifier
                                            .background(Color.Black.copy(alpha = 0.05f), RoundedCornerShape(4.dp))
                                            .padding(2.dp),
                                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                                    ) {
                                        state.forEach { miniItem ->
                                            Box(
                                                modifier = Modifier
                                                    .size(10.dp)
                                                    .background(miniItem.color, RoundedCornerShape(2.dp))
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Active Cards Row
                    val eqIndex = remember(items.size) { items.indexOfFirst { it.id == 5 } }
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items.forEachIndexed { index, item ->
                            key(item.id, item.suffix) {
                                DropTarget<CardItem>(
                                    modifier = Modifier,
                                    onDropped = { draggedItem ->
                                        viewModel.handleDrop(draggedItem, index)
                                    }
                                ) { isInBound, _ ->
                                    val cardContent: @Composable (Boolean) -> Unit = { isBeingDragged ->
                                        Card(
                                            modifier = Modifier
                                                .padding(2.dp)
                                                .widthIn(min = 24.dp)
                                                .height(30.dp)
                                                .pointerInput(item) {
                                                    detectTapGestures(
                                                        onDoubleTap = {
                                                            viewModel.handleSplit(index, item)
                                                        }
                                                    )
                                                },
                                            shape = RoundedCornerShape(12.dp),
                                            colors = CardDefaults.cardColors(
                                                containerColor = if (isInBound) item.color.copy(alpha = 0.5f) else item.color
                                            ),
                                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 0.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                val isFirstOnSide = index == 0 || index == (eqIndex + 1)
                                                val showSign = item.sign != null && (item.sign == "-" || !isFirstOnSide) && item.id != 0

                                                val displayText = if (showSign) "${item.sign} ${item.label}" else item.label
                                                
                                                Text(
                                                    text = "${displayText}${item.suffix}",
                                                    color = if (isLightColor(item.color)) Color.Black else Color.White,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 24.sp
                                                )
                                            }
                                        }
                                    }

                                    if (!item.isDraggable) {
                                        cardContent(false)
                                    } else {
                                        DraggableItem(dataToDrop = item, content = cardContent)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

package org.example.myapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.example.myapp.ui.theme.GreatVibes

// CardItem model
data class CardItem(
    val id: Int,
    val label: String,
    val color: Color,
    val sign: String? = null,
    val isDraggable: Boolean = true,
    val isSplittable: Boolean = true,
    val suffix: String = ""
)

@Composable
fun AlgebraicExpressionsScreen() {
    var isPlaying by remember { mutableStateOf(false) }
    
    data class Config(val id: Int, val label: String, val color: Color, val sign: String?)

    // Initial configuration
    val initialConfig = remember {
        listOf(
            Config(1, "x", Color(0xFFFF5252), "+"),
            Config(2, "y", Color(0xFF448AFF), "+"),
            Config(5, "=", Color(0xFFFFAB40), null),
            Config(3, "z", Color(0xFF7C4DFF), "+")
        )
    }
    
    val items = remember {
        mutableStateListOf<CardItem>().apply {
            addAll(initialConfig.map { config ->
                CardItem(
                    id = config.id,
                    label = config.label,
                    color = config.color,
                    sign = config.sign,
                    isDraggable = config.id != 5,
                    isSplittable = config.id != 5
                )
            })
            // Initial zero check
            val temp = this.toMutableList()
            processZeros(temp)
            this.clear()
            this.addAll(temp)
        }
    }

    val history = remember { mutableStateListOf<List<CardItem>>() }
    val historyScrollState = rememberScrollState()

    // Auto-scroll to the bottom of the history whenever a new entry is added
    LaunchedEffect(history.size) {
        if (history.isNotEmpty()) {
            historyScrollState.animateScrollTo(historyScrollState.maxValue)
        }
    }

    fun saveToHistory() {
        history.add(items.toList())
    }

    fun undo() {
        if (history.isNotEmpty()) {
            val lastState = history.removeAt(history.size - 1)
            items.clear()
            items.addAll(lastState)
        }
    }

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            while (true) {
                delay(1000)
                val moveableIndices = items.indices.filter { items[it].isDraggable }
                if (moveableIndices.isNotEmpty()) {
                    saveToHistory()
                    var success = false
                    var attempts = 0
                    while (!success && attempts < 15) {
                        attempts++
                        val fromIndex = moveableIndices.random()
                        val toIndex = (0 until items.size).random()
                        if (fromIndex == toIndex) continue
                        
                        val temp = items.toMutableList()
                        val eqIdxBefore = temp.indexOfFirst { it.id == 5 }
                        val element = temp.removeAt(fromIndex)
                        temp.add(toIndex, element)
                        
                        processZeros(temp)
                        
                        val finalEqIdx = temp.indexOfFirst { it.id == 5 }
                        val finalElementIdx = temp.indexOfFirst { it.id == element.id && it.suffix == element.suffix }
                        
                        if (finalElementIdx != -1) {
                            val wasOnLeft = fromIndex < eqIdxBefore
                            val isOnLeft = finalElementIdx < finalEqIdx
                            if (wasOnLeft != isOnLeft) {
                                temp[finalElementIdx] = flipSign(temp[finalElementIdx])
                            }
                        }
                        
                        items.clear()
                        items.addAll(temp)
                        success = true
                    }
                }
            }
        }
    }

    DragContainer(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Algebraic Expressions",
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
                                    .clickable(enabled = history.isNotEmpty()) { undo() },
                                tint = if (history.isNotEmpty()) MaterialTheme.colorScheme.primary else Color.Gray
                            )
                            Icon(
                                if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                if (isPlaying) "Pause" else "Play",
                                Modifier
                                    .size(32.dp)
                                    .clickable { isPlaying = !isPlaying }
                            )
                        }
                    }

                    // History (Full Visual List, Scrollable, Auto-scrolling to last)
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items.forEachIndexed { index, item ->
                            DropTarget<CardItem>(
                                modifier = Modifier,
                                onDropped = { draggedItem ->
                                    val fromIdx = items.indexOfFirst { it.id == draggedItem.id && it.suffix == draggedItem.suffix }
                                    if (fromIdx != -1 && fromIdx != index) {
                                        val temp = items.toMutableList()
                                        val eqIdxBefore = temp.indexOfFirst { it.id == 5 }
                                        
                                        val element = temp.removeAt(fromIdx)
                                        temp.add(index, element)
                                        
                                        processZeros(temp)
                                        
                                        val finalEqIdx = temp.indexOfFirst { it.id == 5 }
                                        val finalElementIdx = temp.indexOfFirst { it.id == element.id && it.suffix == element.suffix }
                                        
                                        if (finalElementIdx != -1) {
                                            val wasOnLeft = fromIdx < eqIdxBefore
                                            val isOnLeft = finalElementIdx < finalEqIdx
                                            if (wasOnLeft != isOnLeft) {
                                                temp[finalElementIdx] = flipSign(temp[finalElementIdx])
                                            }
                                        }
                                        
                                        saveToHistory()
                                        items.clear()
                                        items.addAll(temp)
                                    }
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
                                                        if (!item.isSplittable) return@detectTapGestures
                                                        saveToHistory()
                                                        if (item.suffix.isEmpty()) {
                                                            items[index] = item.copy(suffix = "a")
                                                            items.add(index + 1, item.copy(suffix = "b"))
                                                        } else {
                                                            val mergeId = item.id
                                                            val first = items.indexOfFirst { it.id == mergeId }
                                                            if (first != -1) {
                                                                items[first] = items[first].copy(suffix = "")
                                                                items.removeAll { it.id == mergeId && it.suffix.isNotEmpty() }
                                                            }
                                                        }
                                                        val temp = items.toMutableList()
                                                        processZeros(temp)
                                                        items.clear()
                                                        items.addAll(temp)
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
                                            val eqIndex = items.indexOfFirst { it.id == 5 }
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

private fun processZeros(list: MutableList<CardItem>) {
    list.removeAll { it.id == 0 } // Clear existing placeholders
    val eqIdx = list.indexOfFirst { it.id == 5 }
    if (eqIdx == -1) return
    
    // Check left side
    if (eqIdx == 0) {
        list.add(0, CardItem(id = 0, label = "0", color = Color.LightGray, isDraggable = false, isSplittable = false))
    }
    
    // Check right side (equals sign might have moved after adding zero to left)
    val newEqIdx = list.indexOfFirst { it.id == 5 }
    if (newEqIdx == list.size - 1) {
        list.add(CardItem(id = 0, label = "0", color = Color.LightGray, isDraggable = false, isSplittable = false))
    }
}

private fun flipSign(item: CardItem): CardItem {
    val currentSign = item.sign ?: return item
    val newSign = if (currentSign == "+") "-" else "+"
    return item.copy(sign = newSign)
}

private fun isLightColor(color: Color): Boolean {
    val luminance = 0.299 * color.red + 0.587 * color.green + 0.114 * color.blue
    return luminance > 0.5
}

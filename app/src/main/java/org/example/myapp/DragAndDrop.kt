package org.example.myapp

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

internal class DragTargetInfo {
    var isDragging: Boolean by mutableStateOf(false)
    var dragPosition by mutableStateOf(Offset.Zero)
    var dragOffset by mutableStateOf(Offset.Zero)
    var draggableCmp: (@Composable () -> Unit)? by mutableStateOf(null)
    var dataToDrop: Any? by mutableStateOf(null)
}

internal val LocalDragTargetInfo = compositionLocalOf { DragTargetInfo() }

@Composable
fun DragContainer(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val state = remember { DragTargetInfo() }
    CompositionLocalProvider(LocalDragTargetInfo provides state) {
        Box(modifier = modifier) {
            content()
            if (state.isDragging) {
                var targetSize by remember { mutableStateOf(IntSize.Zero) }
                Box(
                    modifier = Modifier
                        .onGloballyPositioned { targetSize = it.size }
                        .graphicsLayer {
                            val offset = state.dragPosition + state.dragOffset
                            translationX = offset.x - targetSize.width / 2
                            translationY = offset.y - targetSize.height / 2
                            alpha = if (targetSize == IntSize.Zero) 0f else .9f
                        }
                ) {
                    state.draggableCmp?.invoke()
                }
            }
        }
    }
}

@Composable
fun <T> DraggableItem(
    modifier: Modifier = Modifier,
    dataToDrop: T,
    onDragStarted: (T) -> Unit = {},
    onDragEnded: (T) -> Unit = {},
    content: @Composable () -> Unit
) {
    var currentPosition by remember { mutableStateOf(Offset.Zero) }
    val currentState = LocalDragTargetInfo.current

    Box(
        modifier = modifier
            .onGloballyPositioned {
                currentPosition = it.localToWindow(Offset.Zero)
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        currentState.isDragging = true
                        currentState.dataToDrop = dataToDrop
                        currentState.draggableCmp = content
                        currentState.dragPosition = currentPosition + it
                        onDragStarted(dataToDrop)
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        currentState.dragOffset += dragAmount
                    },
                    onDragEnd = {
                        currentState.isDragging = false
                        currentState.dragOffset = Offset.Zero
                        onDragEnded(dataToDrop)
                    }
                )
            }
    ) {
        content()
    }
}

@Composable
fun <T> DropTarget(
    modifier: Modifier,
    onDropped: (T) -> Unit,
    content: @Composable BoxScope.(isInBound: Boolean, data: T?) -> Unit
) {
    val dragInfo = LocalDragTargetInfo.current
    val dragPosition = dragInfo.dragPosition + dragInfo.dragOffset
    var isCurrentDropTarget by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.onGloballyPositioned {
            if (dragInfo.isDragging) {
                val area = it.localToWindow(Offset.Zero)
                isCurrentDropTarget = dragPosition.x in area.x..(area.x + it.size.width) &&
                        dragPosition.y in area.y..(area.y + it.size.height)
            }
        }
    ) {
        val data = dragInfo.dataToDrop as? T
        if (isCurrentDropTarget && !dragInfo.isDragging) {
            if (data != null) {
                onDropped(data)
                dragInfo.dataToDrop = null
            }
        }
        content(isCurrentDropTarget, data)
    }
}

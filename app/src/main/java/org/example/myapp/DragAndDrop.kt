package org.example.myapp

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.toSize

internal class DragTargetInfo {
    var isDragging: Boolean by mutableStateOf(false)
    var dragPosition by mutableStateOf(Offset.Zero) // Absolute window touch point
    var dragOffset by mutableStateOf(Offset.Zero)   // Cumulative movement
    var touchOffset by mutableStateOf(Offset.Zero) // Offset within the item where touch started
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
    var containerOffset by remember { mutableStateOf(Offset.Zero) }

    CompositionLocalProvider(LocalDragTargetInfo provides state) {
        Box(modifier = modifier.onGloballyPositioned { 
            containerOffset = it.localToWindow(Offset.Zero) 
        }) {
            content()
            if (state.isDragging) {
                var targetSize by remember { mutableStateOf(IntSize.Zero) }
                Box(
                    modifier = Modifier
                        .onGloballyPositioned { targetSize = it.size }
                        .graphicsLayer {
                            val offset = state.dragPosition + state.dragOffset - state.touchOffset - containerOffset
                            translationX = offset.x
                            translationY = offset.y
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
    content: @Composable (isBeingDragged: Boolean) -> Unit
) {
    var currentPosition by remember { mutableStateOf(Offset.Zero) }
    val currentState = LocalDragTargetInfo.current
    
    val isBeingDragged = currentState.isDragging && currentState.dataToDrop == dataToDrop

    Box(
        modifier = modifier
            .onGloballyPositioned {
                currentPosition = it.localToWindow(Offset.Zero)
            }
            .pointerInput(dataToDrop) {
                detectDragGestures(
                    onDragStart = { touchOffset ->
                        currentState.dataToDrop = dataToDrop
                        currentState.isDragging = true
                        currentState.draggableCmp = { content(true) }
                        currentState.dragPosition = currentPosition + touchOffset
                        currentState.touchOffset = touchOffset
                        currentState.dragOffset = Offset.Zero
                        onDragStarted(dataToDrop)
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        currentState.dragOffset += dragAmount
                    },
                    onDragEnd = {
                        currentState.isDragging = false
                        onDragEnded(dataToDrop)
                    },
                    onDragCancel = {
                        currentState.isDragging = false
                        onDragEnded(dataToDrop)
                    }
                )
            }
    ) {
        Box(modifier = Modifier.graphicsLayer { alpha = if (isBeingDragged) 0f else 1f }) {
            content(false)
        }
    }
}

@Composable
fun <T> DropTarget(
    modifier: Modifier,
    onDropped: (T) -> Unit,
    content: @Composable BoxScope.(isInBound: Boolean, data: T?) -> Unit
) {
    val dragInfo = LocalDragTargetInfo.current
    val dragTouchPoint = dragInfo.dragPosition + dragInfo.dragOffset
    var targetBounds by remember { mutableStateOf<Rect?>(null) }
    
    val isHovering = targetBounds?.contains(dragTouchPoint) ?: false
    val isInBound = dragInfo.isDragging && isHovering

    var wasHoveringBeforeRelease by remember { mutableStateOf(false) }
    if (dragInfo.isDragging) {
        wasHoveringBeforeRelease = isHovering
    }

    LaunchedEffect(dragInfo.isDragging) {
        if (!dragInfo.isDragging && wasHoveringBeforeRelease) {
            val data = dragInfo.dataToDrop as? T
            if (data != null) {
                onDropped(data)
                dragInfo.dataToDrop = null
            }
            wasHoveringBeforeRelease = false
        }
    }

    Box(
        modifier = modifier.onGloballyPositioned {
            val windowOffset = it.localToWindow(Offset.Zero)
            targetBounds = Rect(windowOffset, it.size.toSize())
        }
    ) {
        content(isInBound, dragInfo.dataToDrop as? T)
    }
}

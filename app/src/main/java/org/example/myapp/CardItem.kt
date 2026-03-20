package org.example.myapp

import androidx.compose.ui.graphics.Color

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

fun processZeros(list: MutableList<CardItem>) {
    list.removeAll { it.id == 0 }
    val eqIdx = list.indexOfFirst { it.id == 5 }
    if (eqIdx == -1) return
    
    if (eqIdx == 0) {
        list.add(0, CardItem(id = 0, label = "0", color = Color.LightGray, isDraggable = false, isSplittable = false))
    }
    
    val newEqIdx = list.indexOfFirst { it.id == 5 }
    if (newEqIdx == list.size - 1) {
        list.add(CardItem(id = 0, label = "0", color = Color.LightGray, isDraggable = false, isSplittable = false))
    }
}

fun flipSign(item: CardItem): CardItem {
    val currentSign = item.sign ?: return item
    val newSign = if (currentSign == "+") "-" else "+"
    return item.copy(sign = newSign)
}

fun isLightColor(color: Color): Boolean {
    val luminance = 0.299 * color.red + 0.587 * color.green + 0.114 * color.blue
    return luminance > 0.5
}

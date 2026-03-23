package org.example.myapp

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TutorialViewModel : ViewModel() {

    private val _items = mutableStateListOf<CardItem>()
    val items: List<CardItem> get() = _items

    private val _history = mutableStateListOf<List<CardItem>>()
    val history: List<List<CardItem>> get() = _history

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private var autoPlayJob: Job? = null

    init {
        loadInitialState()
    }

    private data class Config(val id: Int, val label: String, val color: Color, val sign: String?)

    fun togglePlay() {
        _isPlaying.value = !_isPlaying.value
        if (_isPlaying.value) {
            startAutoPlay()
        } else {
            stopAutoPlay()
        }
    }

    private fun startAutoPlay() {
        autoPlayJob?.cancel()
        autoPlayJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                performRandomMove()
            }
        }
    }

    private fun stopAutoPlay() {
        autoPlayJob?.cancel()
        autoPlayJob = null
    }

    private fun performRandomMove() {
        val moveableIndices = _items.indices.filter { _items[it].isDraggable }
        if (moveableIndices.isNotEmpty()) {
            saveToHistory()
            var success = false
            var attempts = 0
            while (!success && attempts < 15) {
                attempts++
                val fromIndex = moveableIndices.random()
                val toIndex = (0 until _items.size).random()
                if (fromIndex == toIndex) continue
                
                val temp = _items.toMutableList()
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
                
                _items.clear()
                _items.addAll(temp)
                success = true
            }
        }
    }

    fun handleDrop(draggedItem: CardItem, targetIndex: Int) {
        val fromIdx = _items.indexOfFirst { it.id == draggedItem.id && it.suffix == draggedItem.suffix }
        if (fromIdx != -1 && fromIdx != targetIndex) {
            saveToHistory()
            val temp = _items.toMutableList()
            val eqIdxBefore = temp.indexOfFirst { it.id == 5 }
            
            val element = temp.removeAt(fromIdx)
            temp.add(targetIndex, element)
            
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
            
            _items.clear()
            _items.addAll(temp)
        }
    }

    fun handleSplit(index: Int, item: CardItem) {
        if (!item.isSplittable) return
        saveToHistory()
        if (item.suffix.isEmpty()) {
            _items[index] = item.copy(suffix = "a")
            _items.add(index + 1, item.copy(suffix = "b"))
        } else {
            val mergeId = item.id
            val first = _items.indexOfFirst { it.id == mergeId }
            if (first != -1) {
                _items[first] = _items[first].copy(suffix = "")
                _items.removeAll { it.id == mergeId && it.suffix.isNotEmpty() }
            }
        }
        val temp = _items.toMutableList()
        processZeros(temp)
        _items.clear()
        _items.addAll(temp)
    }

    fun undo() {
        if (_history.isNotEmpty()) {
            val lastState = _history.removeAt(_history.size - 1)
            _items.clear()
            _items.addAll(lastState)
        }
    }

    fun loadInitialState() {
        _history.clear()
        _items.clear()
        val initialConfig = listOf(
            Config(1, "x", Color(0xFFFF5252), "+"),
            Config(2, "y", Color(0xFF448AFF), "+"),
            Config(5, "=", Color(0xFFFFAB40), null),
            Config(3, "z", Color(0xFF7C4DFF), "+")
        )
        
        _items.addAll(initialConfig.map { config ->
            CardItem(
                id = config.id,
                label = config.label,
                color = config.color,
                sign = config.sign,
                isDraggable = config.id != 5,
                isSplittable = config.id != 5
            )
        })
        val temp = _items.toMutableList()
        processZeros(temp)
        _items.clear()
        _items.addAll(temp)
    }

    private fun saveToHistory() {
        _history.add(_items.toList())
        if (_history.size > 50) {
            _history.removeAt(0)
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopAutoPlay()
    }
}

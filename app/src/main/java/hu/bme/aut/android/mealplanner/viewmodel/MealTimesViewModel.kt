package hu.bme.aut.android.mealplanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.mealplanner.domain.mapper.toDomain
import hu.bme.aut.android.mealplanner.domain.model.MealTime
import hu.bme.aut.android.mealplanner.repository.MealTimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealTimesViewModel @Inject constructor(
    private val mealTimeRepository: MealTimeRepository
) : ViewModel() {

    private val _mealTimes = MutableStateFlow<List<MealTime>>(emptyList())
    val mealTimes: StateFlow<List<MealTime>> = _mealTimes

    init {
        loadMealTimes()
    }

    private fun loadMealTimes() {
        viewModelScope.launch {
            _mealTimes.value = mealTimeRepository.getAll().map { it.toDomain() }
        }
    }

    fun addMealTime(name: String) {
        viewModelScope.launch {
            val newOrder = (_mealTimes.value.maxOfOrNull { it.order } ?: 0) + 1
            val newMealTime = MealTime(id = 0, name = name, order = newOrder)
            mealTimeRepository.insert(newMealTime)
            loadMealTimes()
        }
    }

    fun updateMealTime(mealTime: MealTime) {
        viewModelScope.launch {
            mealTimeRepository.update(mealTime)
            loadMealTimes()
        }
    }

    fun deleteMealTime(mealTime: MealTime) {
        viewModelScope.launch {
            mealTimeRepository.delete(mealTime)
            loadMealTimes()
        }
    }

    fun moveMealTimeUp(mealTime: MealTime) {
        val sorted = _mealTimes.value.sortedBy { it.order }
        val index = sorted.indexOfFirst { it.id == mealTime.id }
        if (index > 0) {
            val above = sorted[index - 1]
            val updated = sorted.toMutableList()
            updated[index - 1] = above.copy(order = mealTime.order)
            updated[index] = mealTime.copy(order = above.order)
            updateOrder(updated)
        }
    }

    fun moveMealTimeDown(mealTime: MealTime) {
        val sorted = _mealTimes.value.sortedBy { it.order }
        val index = sorted.indexOfFirst { it.id == mealTime.id }
        if (index < sorted.size - 1) {
            val below = sorted[index + 1]
            val updated = sorted.toMutableList()
            updated[index + 1] = below.copy(order = mealTime.order)
            updated[index] = mealTime.copy(order = below.order)
            updateOrder(updated)
        }
    }

    private fun updateOrder(mealTimes: List<MealTime>) {
        viewModelScope.launch {
            mealTimes.forEach { mealTimeRepository.update(it) }
            _mealTimes.value = mealTimes
        }
    }
}

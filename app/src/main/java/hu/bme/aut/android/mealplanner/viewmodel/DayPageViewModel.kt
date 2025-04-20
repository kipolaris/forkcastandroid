package hu.bme.aut.android.mealplanner.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.mealplanner.domain.mapper.toDomain
import hu.bme.aut.android.mealplanner.domain.model.Day
import hu.bme.aut.android.mealplanner.domain.model.Meal
import hu.bme.aut.android.mealplanner.domain.model.MealTime
import hu.bme.aut.android.mealplanner.repository.DayRepository
import hu.bme.aut.android.mealplanner.repository.MealTimeRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DayPageViewModel @Inject constructor(
    private val dayRepository: DayRepository,
    private val mealTimeRepository: MealTimeRepository
) : ViewModel() {

    private val _days = MutableStateFlow<List<Day>>(emptyList())
    val days: StateFlow<List<Day>> = _days

    private val _mealTimes = MutableStateFlow<List<MealTime>>(emptyList())
    val mealTimes: StateFlow<List<MealTime>> = _mealTimes

    private val _currentDayIndex = MutableStateFlow(0)
    val currentDayIndex: StateFlow<Int> = _currentDayIndex

    val currentDay: StateFlow<Day?> = combine(_days, _currentDayIndex) { dayList, index ->
        dayList.getOrNull(index)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    init {
        viewModelScope.launch {
            _days.value = dayRepository.getAllWithFullMeals().map { it.toDomain() }
            _mealTimes.value = mealTimeRepository.getAll().map { it.toDomain() }
        }
    }

    fun goToNextDay() {
        val nextIndex = _currentDayIndex.value + 1
        if (nextIndex < _days.value.size) {
            _currentDayIndex.value = nextIndex
        }
    }

    fun goToPreviousDay() {
        val prevIndex = _currentDayIndex.value - 1
        if (prevIndex >= 0) {
            _currentDayIndex.value = prevIndex
        }
    }

    fun setDayIndex(index: Int) {
        if (index in _days.value.indices) {
            _currentDayIndex.value = index
        }
    }
}

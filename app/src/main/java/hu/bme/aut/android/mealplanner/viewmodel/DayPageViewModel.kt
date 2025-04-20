package hu.bme.aut.android.mealplanner.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.mealplanner.data.entity.DayWithMeals
import hu.bme.aut.android.mealplanner.domain.mapper.toDomain
import hu.bme.aut.android.mealplanner.domain.model.Day
import hu.bme.aut.android.mealplanner.repository.DayRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DayPageViewModel @Inject constructor(
    private val dayRepository: DayRepository
) : ViewModel() {

    private val _days = MutableStateFlow<List<Day>>(emptyList())
    val days: StateFlow<List<Day>> = _days

    private val _currentDayIndex = MutableStateFlow(0)
    val currentDayIndex: StateFlow<Int> = _currentDayIndex

    val currentDay: StateFlow<Day?> = combine(_days, _currentDayIndex) { dayList, index ->
        dayList.getOrNull(index)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    init {
        viewModelScope.launch {
            dayRepository.initializeDefaultDays()
            val fullDay = dayRepository.getDayWithFullMeals(1L)
            Log.d("debug", fullDay.toString())
            _days.value = listOf(fullDay.toDomain())
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
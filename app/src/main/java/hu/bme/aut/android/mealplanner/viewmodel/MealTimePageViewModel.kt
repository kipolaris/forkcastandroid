package hu.bme.aut.android.mealplanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.mealplanner.data.entity.MealEntity
import hu.bme.aut.android.mealplanner.data.entity.MealTimeEntity
import hu.bme.aut.android.mealplanner.repository.MealRepository
import hu.bme.aut.android.mealplanner.repository.MealTimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealTimePageViewModel @Inject constructor(
    private val mealTimeRepository: MealTimeRepository,
    private val mealRepository: MealRepository
) : ViewModel() {

    private val _mealTimes = MutableStateFlow<List<MealTimeEntity>>(emptyList())
    val mealTimes: StateFlow<List<MealTimeEntity>> = _mealTimes

    private val _meals = MutableStateFlow<List<MealEntity>>(emptyList())
    val meals: StateFlow<List<MealEntity>> = _meals

    init {
        viewModelScope.launch {
            _mealTimes.value = mealTimeRepository.getAll()
            _meals.value = mealRepository.getMeals()
        }
    }

    fun getMealTime(index: Int): MealTimeEntity? = _mealTimes.value.getOrNull(index)
}
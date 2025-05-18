package hu.bme.aut.android.mealplanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.mealplanner.domain.mapper.toDomain
import hu.bme.aut.android.mealplanner.domain.mapper.toDto
import hu.bme.aut.android.mealplanner.domain.model.Day
import hu.bme.aut.android.mealplanner.domain.model.Food
import hu.bme.aut.android.mealplanner.domain.model.Meal
import hu.bme.aut.android.mealplanner.domain.model.MealTime
import hu.bme.aut.android.mealplanner.network.dto.MealPlanDto
import hu.bme.aut.android.mealplanner.repository.DayRepository
import hu.bme.aut.android.mealplanner.repository.FoodRepository
import hu.bme.aut.android.mealplanner.repository.MealPlanRepository
import hu.bme.aut.android.mealplanner.repository.MealRepository
import hu.bme.aut.android.mealplanner.repository.MealTimeRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DayViewModel @Inject constructor(
    private val dayRepository: DayRepository,
    private val mealTimeRepository: MealTimeRepository,
    private val foodRepository: FoodRepository,
    private val mealRepository: MealRepository,
    private val mealPlanRepository: MealPlanRepository
) : ViewModel() {

    private val _days = MutableStateFlow<List<Day>>(emptyList())
    val days: StateFlow<List<Day>> = _days

    private val _mealTimes = MutableStateFlow<List<MealTime>>(emptyList())
    val mealTimes: StateFlow<List<MealTime>> = _mealTimes

    private val _savedFoods = MutableStateFlow<List<Food>>(emptyList())
    val savedFoods: StateFlow<List<Food>> = _savedFoods

    private val _currentDayIndex = MutableStateFlow(0)

    val currentDay: StateFlow<Day?> = combine(_days, _currentDayIndex) { dayList, index ->
        dayList.getOrNull(index)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    init {
        viewModelScope.launch {
            _days.value = dayRepository.getAllWithFullMeals().map { it.toDomain() }
            _mealTimes.value = mealTimeRepository.getAll().map { it.toDomain() }
            _savedFoods.value = foodRepository.getAll().map { it.toDomain() }
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

    fun moveMealTimeUp(mealTime: MealTime) {
        viewModelScope.launch {
            val sorted = _mealTimes.value.sortedBy { it.order }
            val index = sorted.indexOfFirst { it.id == mealTime.id }
            if (index > 0) {
                val above = sorted[index - 1]
                val updated = sorted.toMutableList()
                updated[index - 1] = above.copy(order = mealTime.order)
                updated[index] = mealTime.copy(order = above.order)

                updated.forEach {
                    mealTimeRepository.update(it)
                }

                _mealTimes.value = updated
            }
        }
    }

    fun moveMealTimeDown(mealTime: MealTime) {
        viewModelScope.launch {
            val sorted = _mealTimes.value.sortedBy { it.order }
            val index = sorted.indexOfFirst { it.id == mealTime.id }
            if (index < sorted.size - 1) {
                val below = sorted[index + 1]
                val updated = sorted.toMutableList()
                updated[index + 1] = below.copy(order = mealTime.order)
                updated[index] = mealTime.copy(order = below.order)

                updated.forEach {
                    mealTimeRepository.update(it)
                }

                _mealTimes.value = updated
            }
        }
    }

    fun addMealTime(name: String) {
        viewModelScope.launch {
            val currentList = _mealTimes.value
            val newOrder = (currentList.maxOfOrNull { it.order } ?: 0) + 1
            val newMealTime = MealTime(
                id = 0,
                name = name,
                order = newOrder
            )
            mealTimeRepository.insert(newMealTime)
            _mealTimes.value = mealTimeRepository.getAll().map { it.toDomain() }
        }
    }

    fun saveNewFood(name: String, description: String, onSaved: (Food) -> Unit) {
        viewModelScope.launch {
            val food = Food(id = 0, name = name, description = description, ingredients = emptyList())
            val saved = foodRepository.insert(food)
            _savedFoods.value = foodRepository.getAll().map { it.toDomain() }
            onSaved(saved)
        }
    }


    fun assignFoodToMeal(dayId: Long, mealTimeId: Long, food: Food) {
        viewModelScope.launch {
            val day = _days.value.find { it.id == dayId } ?: return@launch

            val existingMeal = day.meals.firstOrNull { it.mealTime.id == mealTimeId }

            if (existingMeal != null) {
                val updatedMeal = existingMeal.copy(food = food)
                mealRepository.insertOrUpdateFromDomain(updatedMeal)
            } else {
                val newMeal = Meal(
                    id = 0,
                    dayId = day.id,
                    food = food,
                    mealTime = _mealTimes.value.first { it.id == mealTimeId }
                )
                mealRepository.insertOrUpdateFromDomain(newMeal)
            }

            val updatedDays = dayRepository.getAllWithFullMeals().map { it.toDomain() }

            val updatedMealPlanDto = MealPlanDto(
                days = updatedDays.map { it.toDto() },
                mealTimes = _mealTimes.value.map { it.toDto() }
            )

            mealPlanRepository.updateMealPlan(updatedMealPlanDto)

            _days.value = dayRepository.getAllWithFullMeals().map { it.toDomain() }
        }
    }

    fun resetDayMeals(dayId: Long) {
        viewModelScope.launch {
            val day = _days.value.find { it.id == dayId } ?: return@launch

            day.meals.forEach { meal ->
                val clearedMeal = meal.copy(food = null)
                mealRepository.insertOrUpdateFromDomain(clearedMeal)
            }

            mealPlanRepository.resetDay(day.toDto())

            _days.value = dayRepository.getAllWithFullMeals().map { it.toDomain() }
        }
    }

}

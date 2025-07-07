package hu.bme.aut.android.mealplanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.mealplanner.domain.mapper.toDomain
import hu.bme.aut.android.mealplanner.domain.model.Food
import hu.bme.aut.android.mealplanner.repository.FoodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodsViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : ViewModel() {

    private val _foods = MutableStateFlow<List<Food>>(emptyList())
    val foods: StateFlow<List<Food>> = _foods

    init {
        viewModelScope.launch {
            _foods.value = foodRepository.getAll().map { it.toDomain() }
        }
    }

    fun addFood(name: String, description: String) {
        viewModelScope.launch {
            val newFood = Food(id = 0, name = name, description = description, ingredients = emptyList())
            foodRepository.insert(newFood)
            _foods.value = foodRepository.getAll().map { it.toDomain() }
        }
    }

    fun deleteFood(food: Food) {
        viewModelScope.launch {
            foodRepository.delete(food)
            _foods.value = foodRepository.getAll().map { it.toDomain() }
        }
    }

    fun editFoodName(food: Food) {
        viewModelScope.launch {
            foodRepository.update(food)
            _foods.value = foodRepository.getAll().map { it.toDomain() }
        }
    }
}

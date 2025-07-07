package hu.bme.aut.android.mealplanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.mealplanner.domain.mapper.toDomain
import hu.bme.aut.android.mealplanner.domain.model.Ingredient
import hu.bme.aut.android.mealplanner.repository.IngredientRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientsViewModel @Inject constructor(
    private val ingredientRepository: IngredientRepository
) : ViewModel() {

    private val _ingredients = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredients: StateFlow<List<Ingredient>> = _ingredients

    init {
        viewModelScope.launch {
            _ingredients.value = ingredientRepository.getAll().map { it.toDomain() }
        }
    }

    fun addIngredient(name: String) {
        viewModelScope.launch {
            val newIngredient = Ingredient(id = 0, name = name)
            ingredientRepository.insert(newIngredient)
            _ingredients.value = ingredientRepository.getAll().map { it.toDomain() }
        }
    }

    fun updateIngredient(ingredient: Ingredient) {
        viewModelScope.launch {
            ingredientRepository.update(ingredient)
            _ingredients.value = ingredientRepository.getAll().map { it.toDomain() }
        }
    }

    fun deleteIngredient(ingredient: Ingredient) {
        viewModelScope.launch {
            ingredientRepository.delete(ingredient)
            _ingredients.value = ingredientRepository.getAll().map { it.toDomain() }
        }
    }
}

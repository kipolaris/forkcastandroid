package hu.bme.aut.android.mealplanner.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.mealplanner.domain.mapper.toDomain
import hu.bme.aut.android.mealplanner.domain.model.Food
import hu.bme.aut.android.mealplanner.domain.model.Ingredient
import hu.bme.aut.android.mealplanner.repository.FoodRepository
import hu.bme.aut.android.mealplanner.repository.IngredientRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val foodRepository: FoodRepository,
    private val ingredientRepository: IngredientRepository
) : ViewModel() {

    private val foodId: Long = checkNotNull(savedStateHandle["foodId"]).toString().toLong()

    private val _food = MutableStateFlow<Food?>(null)
    val food: StateFlow<Food?> = _food

    private val _ingredients = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredients: StateFlow<List<Ingredient>> = _ingredients

    init {
        viewModelScope.launch {
            _ingredients.value = ingredientRepository.getAll().map { it.toDomain() }
            _food.value = foodRepository.getByIdWithIngredients(foodId)
        }
    }

    fun updateIngredientQuantity(ingredientId: Long, newQuantity: String) {
        viewModelScope.launch {
            _food.value = _food.value?.copy(
                ingredients = _food.value?.ingredients?.map {
                    if (it.id == ingredientId) it.copy(quantity = newQuantity) else it
                }
            )
            _food.value?.let { foodRepository.update(it) }
        }
    }

    fun removeIngredient(ingredientId: Long) {
        viewModelScope.launch {
            _food.value = _food.value?.copy(
                ingredients = _food.value?.ingredients?.filterNot { it.id == ingredientId }
            )
            _food.value?.let { foodRepository.update(it) }
        }
    }

    fun addIngredient(ingredient: Ingredient, quantity: String) {
        viewModelScope.launch {
            val updatedList = _food.value!!.ingredients?.toMutableList()
            updatedList?.add(ingredient.copy(quantity = quantity))
            _food.value = _food.value?.copy(ingredients = updatedList)
            _food.value?.let { foodRepository.update(it) }
        }
    }


    fun addNewIngredientAndAssign(name: String, quantity: String) {
        viewModelScope.launch {
            val ingredientId = ingredientRepository.insert(Ingredient(id = 0L, name = name, quantity = null))
            val newIngredient = ingredientRepository.getById(ingredientId)

            _ingredients.value = ingredientRepository.getAll().map { it.toDomain() }

            addIngredient(newIngredient, quantity)
        }
    }

    fun updateFoodDescription(newDesc: String) {
        viewModelScope.launch {
            _food.value = _food.value?.copy(description = newDesc)
            _food.value?.let { foodRepository.update(it) }
        }
    }
}

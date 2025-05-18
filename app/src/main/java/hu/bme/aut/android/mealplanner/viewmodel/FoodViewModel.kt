package hu.bme.aut.android.mealplanner.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.mealplanner.domain.mapper.toDomain
import hu.bme.aut.android.mealplanner.domain.model.Food
import hu.bme.aut.android.mealplanner.domain.model.FoodIngredient
import hu.bme.aut.android.mealplanner.domain.model.Ingredient
import hu.bme.aut.android.mealplanner.domain.model.UnitOfMeasure
import hu.bme.aut.android.mealplanner.repository.FoodRepository
import hu.bme.aut.android.mealplanner.repository.IngredientRepository
import hu.bme.aut.android.mealplanner.repository.UnitOfMeasureRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val foodRepository: FoodRepository,
    private val ingredientRepository: IngredientRepository,
    private val unitOfMeasureRepository: UnitOfMeasureRepository
) : ViewModel() {

    private val foodId: Long = checkNotNull(savedStateHandle["foodId"]).toString().toLong()

    private val _food = MutableStateFlow<Food?>(null)
    val food: StateFlow<Food?> = _food

    private val _ingredients = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredients: StateFlow<List<Ingredient>> = _ingredients

    private val _units = MutableStateFlow<List<UnitOfMeasure>>(emptyList())
    val units: StateFlow<List<UnitOfMeasure>> = _units

    init {
        viewModelScope.launch {
            val foodRaw = foodRepository.getFood(foodId)
            val foodIngredients = foodRepository.getIngredientsWithAmount(foodId)
            _food.value = foodRaw.toDomain(foodIngredients)

            _ingredients.value = ingredientRepository.getAll().map { it.toDomain() }

            if (unitOfMeasureRepository.getAll().isEmpty()) unitOfMeasureRepository.initializeUnitsOfMeasure()
            _units.value = unitOfMeasureRepository.getAll().map { it.toDomain() }
        }
    }

    fun updateIngredientQuantity(ingredientId: Long, newAmount: Double, unit: UnitOfMeasure) {
        viewModelScope.launch {
            _food.value = _food.value?.copy(
                ingredients = _food.value?.ingredients?.map {
                    if (it.ingredient.id == ingredientId) it.copy(amount = newAmount, unit = unit) else it
                }
            )
            _food.value?.let { foodRepository.update(it) }
        }
    }

    fun removeIngredient(ingredientId: Long) {
        viewModelScope.launch {
            _food.value = _food.value?.copy(
                ingredients = _food.value?.ingredients?.filterNot { it.ingredient.id == ingredientId }
            )
            _food.value?.let { foodRepository.update(it) }
        }
    }

    fun addIngredient(ingredient: Ingredient, amount: Double, unit: UnitOfMeasure) {
        viewModelScope.launch {
            val updatedList = _food.value!!.ingredients?.toMutableList()
            updatedList?.add(FoodIngredient(ingredient = ingredient, amount = amount, unit = unit))
            _food.value = _food.value?.copy(ingredients = updatedList)
            _food.value?.let { foodRepository.update(it) }
        }
    }


    fun addNewIngredientAndAssign(name: String, amount: Double, unit: UnitOfMeasure) {
        viewModelScope.launch {
            val ingredient = ingredientRepository.insert(Ingredient(id = 0L, name = name))
            val newIngredient = ingredientRepository.getById(ingredient.id)

            _ingredients.value = ingredientRepository.getAll().map { it.toDomain() }

            addIngredient(newIngredient, amount, unit)
        }
    }

    fun updateFoodDescription(newDesc: String) {
        viewModelScope.launch {
            _food.value = _food.value?.copy(description = newDesc)
            _food.value?.let { foodRepository.update(it) }
        }
    }
}

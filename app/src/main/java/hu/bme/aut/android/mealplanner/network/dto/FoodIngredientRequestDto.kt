package hu.bme.aut.android.mealplanner.network.dto

data class FoodIngredientRequestDto(
    val ingredientId: Long,
    val amount: Double,
    val unitId: Long
)
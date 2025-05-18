package hu.bme.aut.android.mealplanner.network.dto

data class FoodIngredientDto(
    val foodId: Long,
    val ingredientId: Long,
    val amount: Double,
    val unit: UnitOfMeasureDto
)

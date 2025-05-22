package hu.bme.aut.android.mealplanner.network.dto

data class FoodIngredientDto(
    val id: Long?,
    val foodId: Long,
    val ingredient: IngredientDto,
    val amount: Double,
    val unit: UnitOfMeasureDto
)

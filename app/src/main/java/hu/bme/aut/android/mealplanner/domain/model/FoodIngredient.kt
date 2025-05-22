package hu.bme.aut.android.mealplanner.domain.model

data class FoodIngredient(
    val id: Long?,
    val ingredient: Ingredient,
    val amount: Double,
    val unit: UnitOfMeasure
)
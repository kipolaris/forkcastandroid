package hu.bme.aut.android.mealplanner.domain.model

data class FoodIngredient(
    val ingredient: Ingredient,
    val amount: Double,
    val unit: UnitOfMeasure
)
package hu.bme.aut.android.mealplanner.domain.model

data class Food(
    val id: Long,
    val name: String,
    val description: String?,
    val ingredients: List<FoodIngredient>?
)
package hu.bme.aut.android.mealplanner.network.dto

data class FoodDto(
    val id: Long?,
    val name: String,
    val description: String? = null,
    val ingredients: List<IngredientDto>
)

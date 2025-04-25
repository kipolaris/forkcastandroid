package hu.bme.aut.android.mealplanner.data.relation

data class FoodWithIngredientRaw(
    val foodId: Long,
    val foodName: String,
    val foodDescription: String?,
    val ingredientId: Long?,
    val ingredientName: String?,
    val quantityInCrossRef: String?
)

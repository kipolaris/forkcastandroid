package hu.bme.aut.android.mealplanner.data.entity

data class FoodWithIngredients(
    @Embedded val food: FoodEntity,
    @Relation( parentColumn = "id", entityColumn = "foodId" )
    val ingredients: List<IngredientEntity>
)
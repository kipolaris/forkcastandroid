package hu.bme.aut.android.mealplanner.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class FoodWithIngredients(
    @Embedded val food: FoodEntity,
    @Relation( parentColumn = "id", entityColumn = "foodId" )
    val ingredients: List<IngredientEntity>
)
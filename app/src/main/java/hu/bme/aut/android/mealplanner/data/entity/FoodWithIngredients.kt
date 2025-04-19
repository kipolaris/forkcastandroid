package hu.bme.aut.android.mealplanner.data.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class FoodWithIngredients(
    @Embedded val food: FoodEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = FoodIngredientCrossRef::class,
            parentColumn = "foodId",
            entityColumn = "ingredientId"
        )
    )
    val ingredients: List<IngredientEntity>
)
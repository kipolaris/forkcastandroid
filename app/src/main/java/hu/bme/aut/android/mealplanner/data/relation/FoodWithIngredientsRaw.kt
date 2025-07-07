package hu.bme.aut.android.mealplanner.data.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import hu.bme.aut.android.mealplanner.data.entity.FoodEntity
import hu.bme.aut.android.mealplanner.data.entity.IngredientEntity

data class FoodWithIngredientsRaw(
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



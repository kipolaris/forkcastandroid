package hu.bme.aut.android.mealplanner.data.relation

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "food_ingredient_cross_ref",
    primaryKeys = ["foodId", "ingredientId"],
    indices = [
        Index(value = ["foodId"]),
        Index(value = ["ingredientId"])
    ]
)
data class FoodIngredientCrossRef(
    val foodId: Long,
    val ingredientId: Long,
    val quantity: String? = null
)

package hu.bme.aut.android.mealplanner.data.entity

import androidx.room.Entity

@Entity(
    tableName = "food_ingredient_cross_ref",
    primaryKeys = ["foodId", "ingredientId"]
)
data class FoodIngredientCrossRef(
    val foodId: Long,
    val ingredientId: Long
)
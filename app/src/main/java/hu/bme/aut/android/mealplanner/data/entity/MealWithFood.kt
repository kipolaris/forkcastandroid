package hu.bme.aut.android.mealplanner.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class MealWithFood(
    @Embedded val meal: MealEntity,

    @Relation(
        parentColumn = "foodId",
        entityColumn = "id"
    )
    val food: FoodEntity?,

    @Relation(
        parentColumn = "mealTimeId",
        entityColumn = "id"
    )
    val mealTime: MealTimeEntity
)

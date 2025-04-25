package hu.bme.aut.android.mealplanner.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import hu.bme.aut.android.mealplanner.data.entity.FoodEntity
import hu.bme.aut.android.mealplanner.data.entity.MealEntity
import hu.bme.aut.android.mealplanner.data.entity.MealTimeEntity

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

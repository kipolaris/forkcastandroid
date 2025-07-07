package hu.bme.aut.android.mealplanner.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import hu.bme.aut.android.mealplanner.data.entity.DayEntity
import hu.bme.aut.android.mealplanner.data.entity.MealEntity

data class DayWithMeals(
    @Embedded val day: DayEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "dayId"
    )
    val meals: List<MealEntity>
)

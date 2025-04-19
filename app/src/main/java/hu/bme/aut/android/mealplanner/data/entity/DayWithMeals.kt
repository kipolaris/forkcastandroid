package hu.bme.aut.android.mealplanner.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class DayWithMeals(
    @Embedded val day: DayEntity,
    @Relation( parentColumn = "id", entityColumn = "dayId" )
    val meals: List<MealEntity>
)
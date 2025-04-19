package hu.bme.aut.android.mealplanner.data.entity

data class DayWithMeals(
    @Embedded val day: DayEntity,
    @Relation( parentColumn = "id", entityColumn = "dayId" )
    val meals: List<MealEntity>
)
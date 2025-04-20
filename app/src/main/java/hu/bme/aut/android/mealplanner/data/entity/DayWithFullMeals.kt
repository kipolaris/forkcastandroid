package hu.bme.aut.android.mealplanner.data.entity

data class DayWithFullMeals(
    val day: DayEntity,
    val meals: List<MealWithFood>
)

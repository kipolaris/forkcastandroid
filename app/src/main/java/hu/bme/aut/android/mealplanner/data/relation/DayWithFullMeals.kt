package hu.bme.aut.android.mealplanner.data.relation

import hu.bme.aut.android.mealplanner.data.entity.DayEntity

data class DayWithFullMeals(
    val day: DayEntity,
    val meals: List<MealWithFood>
)



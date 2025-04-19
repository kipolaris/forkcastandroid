package hu.bme.aut.android.mealplanner.domain.mapper

import hu.bme.aut.android.mealplanner.data.entity.DayWithMeals
import hu.bme.aut.android.mealplanner.domain.model.Day

fun DayWithMeals.toDomain(): Day = Day(
    id = day.id,
    name = day.name,
    meals = meals.map { it.toDomain() }
)

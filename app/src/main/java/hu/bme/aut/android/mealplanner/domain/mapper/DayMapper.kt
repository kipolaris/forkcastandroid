package hu.bme.aut.android.mealplanner.domain.mapper

import hu.bme.aut.android.mealplanner.data.entity.DayEntity
import hu.bme.aut.android.mealplanner.data.relation.DayWithFullMeals
import hu.bme.aut.android.mealplanner.domain.model.Day
import hu.bme.aut.android.mealplanner.domain.model.Meal
import hu.bme.aut.android.mealplanner.network.dto.DayDto
import hu.bme.aut.android.mealplanner.network.dto.MealDto

fun DayDto.toEntity(): DayEntity = DayEntity(
    id = this.id,
    name = this.name
)

fun DayEntity.toDto(meals: List<MealDto>): DayDto = DayDto(
    id = this.id,
    name = this.name,
    meals = meals
)

fun DayDto.toDomain() = Day(
    id = id,
    name = name,
    meals = meals.map { it.toDomain() }
)

fun DayWithFullMeals.toDomain(): Day = Day(
    id = day.id,
    name = day.name,
    meals = meals.map { it.toDomain() }
)

fun DayEntity.toDomain(meals: List<Meal>): Day = Day(
    id = id,
    name = name,
    meals = meals
)

fun Day.toEntity(): DayEntity = DayEntity(
    id = id,
    name = name
)

fun Day.toDto(): DayDto = DayDto(
    id = id,
    name = name,
    meals = meals.map { it.toDto() }
)


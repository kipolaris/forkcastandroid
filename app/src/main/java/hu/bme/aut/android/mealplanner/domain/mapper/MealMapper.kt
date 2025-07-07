package hu.bme.aut.android.mealplanner.domain.mapper

import hu.bme.aut.android.mealplanner.data.entity.*
import hu.bme.aut.android.mealplanner.data.relation.MealWithFood
import hu.bme.aut.android.mealplanner.domain.model.Meal
import hu.bme.aut.android.mealplanner.network.dto.MealDto

fun MealDto.toEntity(): MealEntity = MealEntity(
    id = this.id ?: 0,
    foodId = this.food?.toEntity()?.id,
    mealTimeId = this.mealTime.toEntity().id,
    dayId = this.dayId
)

fun MealDto.toDomain() = Meal(
    id = id ?: 0,
    food = food?.toDomain(),
    mealTime = mealTime.toDomain(),
    dayId = dayId
)

fun Meal.toEntity() = MealEntity(
    id = id,
    foodId = food?.toEntity()?.id,
    mealTimeId = mealTime.toEntity().id,
    dayId = dayId
)

fun Meal.toDto() = MealDto(
    id = id,
    food = food?.toDto(),
    mealTime = mealTime.toDto(),
    dayId = dayId
)

fun MealWithFood.toDomain(): Meal = Meal(
    id = meal.id,
    food = food?.toDomain(emptyList()),
    mealTime = mealTime.toDomain(),
    dayId = meal.dayId
)

fun MealWithFood.toDto(): MealDto = MealDto(
    id = meal.id,
    food = food?.toDto(emptyList()),
    mealTime = mealTime.toDto(),
    dayId = meal.dayId
)
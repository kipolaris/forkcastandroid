package hu.bme.aut.android.mealplanner.domain.mapper

import hu.bme.aut.android.mealplanner.data.entity.MealEntity
import hu.bme.aut.android.mealplanner.domain.model.Meal
import hu.bme.aut.android.mealplanner.network.dto.MealDto

fun MealDto.toEntity(): MealEntity = MealEntity(
    id = this.id ?: 0,
    foodId = this.foodId,
    mealTimeId = this.mealTimeId,
    dayId = this.dayId
)

fun MealEntity.toDto(): MealDto = MealDto(
    id = this.id,
    foodId = this.foodId,
    mealTimeId = this.mealTimeId,
    dayId = this.dayId
)

fun MealDto.toDomain() = Meal(
    id ?: 0,
    foodId,
    mealTimeId,
    dayId
)

fun MealEntity.toDomain() = Meal(
    id,
    foodId,
    mealTimeId,
    dayId
)

fun Meal.toEntity() = MealEntity(
    id,
    foodId,
    mealTimeId,
    dayId
)

fun Meal.toDto() = MealDto(
    id,
    foodId,
    mealTimeId,
    dayId
)
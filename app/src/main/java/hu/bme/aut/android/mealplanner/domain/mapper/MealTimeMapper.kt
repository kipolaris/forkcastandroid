package hu.bme.aut.android.mealplanner.domain.mapper

import hu.bme.aut.android.mealplanner.data.entity.MealTimeEntity
import hu.bme.aut.android.mealplanner.domain.model.MealTime
import hu.bme.aut.android.mealplanner.network.dto.MealTimeDto

fun MealTimeDto.toEntity(): MealTimeEntity = MealTimeEntity(
    id = this.id ?: 0,
    name = this.name,
    order = this.order
)

fun MealTimeEntity.toDto(): MealTimeDto = MealTimeDto(
    id = this.id,
    name = this.name,
    order = this.order
)

fun MealTimeEntity.toDomain(): MealTime = MealTime(
    id = id,
    name = name,
    order = order
)

fun MealTime.toEntity(): MealTimeEntity = MealTimeEntity(
    id = id,
    name = name,
    order = order
)

fun MealTime.toDto(): MealTimeDto = MealTimeDto(
    id = id,
    name = name,
    order = order
)

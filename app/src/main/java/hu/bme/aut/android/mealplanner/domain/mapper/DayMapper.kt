package hu.bme.aut.android.mealplanner.domain.mapper

import hu.bme.aut.android.mealplanner.data.entity.DayEntity
import hu.bme.aut.android.mealplanner.domain.model.Day
import hu.bme.aut.android.mealplanner.network.dto.DayDto

fun DayDto.toEntity(): DayEntity = DayEntity(
    id = this.id,
    name = this.name
)

fun DayEntity.toDto(): DayDto = DayDto(
    id = this.id,
    name = this.name
)

fun DayDto.toDomain() = Day(
    id = id,
    name = name,
    meals = emptyList()
)

fun DayEntity.toDomain() = Day(
    id = id,
    name = name,
    meals = emptyList()
)

fun Day.toEntity() = DayEntity(
    id = id,
    name = name
)

fun Day.toDto() = DayDto(
    id = id,
    name = name
)

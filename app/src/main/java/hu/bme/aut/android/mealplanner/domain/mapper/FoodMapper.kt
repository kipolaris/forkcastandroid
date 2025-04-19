package hu.bme.aut.android.mealplanner.domain.mapper

import hu.bme.aut.android.mealplanner.data.entity.FoodEntity
import hu.bme.aut.android.mealplanner.domain.model.Food
import hu.bme.aut.android.mealplanner.network.dto.FoodDto

fun FoodDto.toEntity(): FoodEntity = FoodEntity(
    id = this.id ?: 0,
    name = this.name,
    description = this.description
)

fun FoodEntity.toDto(): FoodDto = FoodDto(
    id = this.id,
    name = this.name,
    description = this.description,
    ingredientIds = null
)

fun FoodEntity.toDomain(): Food = Food(
    id = id,
    name = name,
    description = description,
    ingredients = emptyList()
)

fun Food.toEntity(): FoodEntity = FoodEntity(
    id = id,
    name = name,
    description = description
)

fun Food.toDto(): FoodDto = FoodDto(
    id = id,
    name = name,
    description = description,
    ingredientIds = ingredients.map { it.id }
)

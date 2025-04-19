package hu.bme.aut.android.mealplanner.domain.mapper

import hu.bme.aut.android.mealplanner.data.entity.FoodEntity
import hu.bme.aut.android.mealplanner.domain.model.Food
import hu.bme.aut.android.mealplanner.domain.model.Ingredient
import hu.bme.aut.android.mealplanner.network.dto.FoodDto
import hu.bme.aut.android.mealplanner.network.dto.IngredientDto

fun FoodDto.toEntity(): FoodEntity = FoodEntity(
    id = this.id ?: 0,
    name = this.name,
    description = this.description
)

fun FoodEntity.toDto(ingredients: List<IngredientDto>): FoodDto = FoodDto(
    id = this.id,
    name = this.name,
    description = this.description,
    ingredients = ingredients
)

fun FoodDto.toDomain(): Food = Food(
    id = id ?: 0,
    name = name,
    description = description,
    ingredients = ingredients.map { it.toDomain() }
)

fun FoodEntity.toDomain(ingredients: List<Ingredient>): Food = Food(
    id = id,
    name = name,
    description = description,
    ingredients = ingredients
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
    ingredients = ingredients.map { it.toDto() }
)

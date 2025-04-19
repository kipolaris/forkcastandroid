package hu.bme.aut.android.mealplanner.domain.mapper

import hu.bme.aut.android.mealplanner.data.entity.IngredientEntity
import hu.bme.aut.android.mealplanner.domain.model.Ingredient
import hu.bme.aut.android.mealplanner.network.dto.IngredientDto

fun IngredientDto.toEntity(foodId: Long): IngredientEntity = IngredientEntity(
    id = this.id,
    name = this.name,
    quantity = this.quantity,
    foodId = foodId
)

fun IngredientEntity.toDto(): IngredientDto = IngredientDto(
    id = this.id,
    name = this.name,
    quantity = this.quantity
)

fun IngredientEntity.toDomain(): Ingredient = Ingredient(
    id = id,
    name = name,
    quantity = quantity
)

fun Ingredient.toEntity(foodId: Long): IngredientEntity = IngredientEntity(
    id = id,
    name = name,
    quantity = quantity,
    foodId = foodId
)

fun Ingredient.toDto(): IngredientDto = IngredientDto(
    id = id,
    name = name,
    quantity = quantity
)

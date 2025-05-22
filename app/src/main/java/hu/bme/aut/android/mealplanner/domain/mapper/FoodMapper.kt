package hu.bme.aut.android.mealplanner.domain.mapper

import hu.bme.aut.android.mealplanner.data.entity.FoodEntity
import hu.bme.aut.android.mealplanner.data.relation.FoodWithIngredientsRaw
import hu.bme.aut.android.mealplanner.data.relation.IngredientWithAmount
import hu.bme.aut.android.mealplanner.domain.model.Food
import hu.bme.aut.android.mealplanner.domain.model.FoodIngredient
import hu.bme.aut.android.mealplanner.network.dto.FoodDto
import hu.bme.aut.android.mealplanner.network.dto.FoodIngredientDto

fun FoodDto.toEntity(): FoodEntity = FoodEntity(
    id = this.id ?: 0,
    name = this.name,
    description = this.description
)

fun FoodEntity.toDto(ingredients: List<FoodIngredientDto> = emptyList()): FoodDto = FoodDto(
    id = this.id,
    name = this.name,
    description = this.description,
    ingredients = ingredients
)

fun FoodDto.toDomain(): Food = Food(
    id = id ?: 0,
    name = name,
    description = description,
    ingredients = ingredients?.map { it.toDomain() }
)

fun FoodEntity.toDomain(ingredients: List<FoodIngredient> = emptyList()): Food = Food(
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
    id = this.id,
    name = this.name,
    description = this.description,
    ingredients = this.ingredients!!.map { it.toDto() }
)

fun FoodWithIngredientsRaw.toDomain(ingredientsWithAmount: List<IngredientWithAmount>): Food = Food(
    id = food.id,
    name = food.name,
    description = food.description,
    ingredients = ingredientsWithAmount.map {
        FoodIngredient(
            id = null,
            ingredient = it.ingredient.toDomain(),
            amount = it.amount,
            unit = it.unit.toDomain()
        )
    }
)


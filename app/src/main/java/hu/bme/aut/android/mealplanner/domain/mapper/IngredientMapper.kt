package hu.bme.aut.android.mealplanner.domain.mapper

import hu.bme.aut.android.mealplanner.data.entity.IngredientEntity
import hu.bme.aut.android.mealplanner.data.entity.UnitOfMeasureEntity
import hu.bme.aut.android.mealplanner.data.relation.FoodIngredientCrossRef
import hu.bme.aut.android.mealplanner.domain.model.FoodIngredient
import hu.bme.aut.android.mealplanner.domain.model.Ingredient
import hu.bme.aut.android.mealplanner.domain.model.UnitOfMeasure
import hu.bme.aut.android.mealplanner.network.dto.FoodIngredientDto
import hu.bme.aut.android.mealplanner.network.dto.IngredientDto
import hu.bme.aut.android.mealplanner.network.dto.UnitOfMeasureDto

fun IngredientDto.toEntity(): IngredientEntity = IngredientEntity(
    id = this.id,
    name = this.name
)

fun IngredientEntity.toDto(): IngredientDto = IngredientDto(
    id = this.id,
    name = this.name
)

fun IngredientEntity.toDomain(): Ingredient = Ingredient(
    id = id,
    name = name
)

fun Ingredient.toEntity(): IngredientEntity = IngredientEntity(
    id = id,
    name = name
)

fun Ingredient.toDto(): IngredientDto = IngredientDto(
    id = id,
    name = name
)

fun IngredientDto.toDomain(): Ingredient = Ingredient(
    id = id,
    name = name
)

fun UnitOfMeasureDto.toEntity(): UnitOfMeasureEntity = UnitOfMeasureEntity(
    id = id,
    name = name,
    abbreviation = abbreviation,
    type = type,
    multiplierToBase = multiplierToBase
)

fun UnitOfMeasureDto.toDomain(): UnitOfMeasure = UnitOfMeasure(
    id = id,
    name = name,
    abbreviation = abbreviation,
    type = type,
    multiplierToBase = multiplierToBase
)

fun UnitOfMeasure.toDto(): UnitOfMeasureDto = UnitOfMeasureDto(
    id = id,
    name = name,
    abbreviation = abbreviation,
    type = type,
    multiplierToBase = multiplierToBase
)

fun UnitOfMeasureEntity.toDomain(): UnitOfMeasure = UnitOfMeasure(
    id = id,
    name = name,
    abbreviation = abbreviation,
    type = type,
    multiplierToBase = multiplierToBase
)

fun FoodIngredientDto.toCrossRef(): FoodIngredientCrossRef = FoodIngredientCrossRef(
    foodId = foodId,
    ingredientId = ingredient.id,
    amount = amount,
    unitId = unit.id
)

fun FoodIngredientDto.toDomain(): FoodIngredient = FoodIngredient(
    id = id,
    ingredient = Ingredient(id = ingredient.id, name = ""),
    amount = amount,
    unit = unit.toDomain()
)

fun FoodIngredient.toDto(): FoodIngredientDto = FoodIngredientDto(
    id = id,
    foodId = 0L,
    ingredient = ingredient.toDto(),
    amount = amount,
    unit = unit.toDto()
)
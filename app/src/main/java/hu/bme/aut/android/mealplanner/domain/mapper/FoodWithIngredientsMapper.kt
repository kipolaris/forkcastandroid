package hu.bme.aut.android.mealplanner.domain.mapper

import hu.bme.aut.android.mealplanner.data.entity.FoodWithIngredients
import hu.bme.aut.android.mealplanner.data.entity.IngredientEntity
import hu.bme.aut.android.mealplanner.domain.model.Food
import hu.bme.aut.android.mealplanner.domain.model.Ingredient

fun FoodWithIngredients.toDomain(): Food = Food(
    id = food.id,
    name = food.name,
    description = food.description,
    ingredients = ingredients.map { it.toDomain() }
)

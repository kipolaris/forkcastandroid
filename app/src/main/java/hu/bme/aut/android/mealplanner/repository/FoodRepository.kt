package hu.bme.aut.android.mealplanner.repository

import hu.bme.aut.android.mealplanner.data.dao.FoodDao
import hu.bme.aut.android.mealplanner.data.dao.FoodIngredientCrossRefDao
import hu.bme.aut.android.mealplanner.data.entity.FoodEntity
import hu.bme.aut.android.mealplanner.data.relation.FoodIngredientCrossRef
import hu.bme.aut.android.mealplanner.domain.mapper.toEntity
import hu.bme.aut.android.mealplanner.domain.model.Food
import hu.bme.aut.android.mealplanner.domain.model.Ingredient
import hu.bme.aut.android.mealplanner.network.api.FoodApi

class FoodRepository(
    private val api: FoodApi,
    private val dao: FoodDao,
    private val crossRefDao: FoodIngredientCrossRefDao
) {
    suspend fun getAll(): List<FoodEntity> = dao.getAll()

    suspend fun insertAll(items: List<FoodEntity>) = dao.insertAll(items)

    suspend fun deleteAll() = dao.deleteAll()

    suspend fun syncFoods() {
        try {
            val foodDtos = api.getAllFoods()
            val foodEntities = foodDtos.map { it.toEntity() }
            val crossRefs = foodDtos.flatMap { food ->
                food.ingredients.map { ingredient ->
                    FoodIngredientCrossRef(
                        foodId = food.id ?: 0,
                        ingredientId = ingredient.id
                    )
                }
            }

            dao.deleteAll()
            crossRefDao.deleteAll()
            dao.insertAll(foodEntities)
            crossRefDao.insertAll(crossRefs)

        } catch (e: Exception) {
            // TODO fallback logic
        }
    }

    suspend fun insert(food: Food): Food {
        val foodEntity = food.toEntity()
        val newId = dao.insert(foodEntity)
        return food.copy(id = newId)
    }

    suspend fun delete(food: Food) {
        dao.delete(food.toEntity())
    }

    suspend fun update(food: Food) {
        val foodEntity = food.toEntity()
        dao.update(foodEntity)

        val crossRefs = food.ingredients.orEmpty().map {
            FoodIngredientCrossRef(
                foodId = food.id,
                ingredientId = it.id,
                quantity = it.quantity
            )
        }

        crossRefDao.deleteForFood(food.id)
        crossRefDao.insertAll(crossRefs)
    }

    suspend fun getByIdWithIngredients(foodId: Long): Food {
        val rawItems = dao.getFoodWithIngredientsRaw(foodId)
        val first = rawItems.firstOrNull()
            ?: throw IllegalArgumentException("Food not found")

        val food = Food(
            id = first.foodId,
            name = first.foodName,
            description = first.foodDescription,
            ingredients = rawItems.mapNotNull {
                if (it.ingredientId != null && it.ingredientName != null) {
                    Ingredient(
                        id = it.ingredientId,
                        name = it.ingredientName,
                        quantity = it.quantityInCrossRef
                    )
                } else null
            }
        )
        return food
    }
}

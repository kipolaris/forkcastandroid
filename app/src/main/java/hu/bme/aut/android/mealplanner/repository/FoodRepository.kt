package hu.bme.aut.android.mealplanner.repository

import hu.bme.aut.android.mealplanner.data.dao.FoodDao
import hu.bme.aut.android.mealplanner.data.dao.FoodIngredientCrossRefDao
import hu.bme.aut.android.mealplanner.data.entity.FoodEntity
import hu.bme.aut.android.mealplanner.data.entity.FoodIngredientCrossRef
import hu.bme.aut.android.mealplanner.domain.mapper.toDomain
import hu.bme.aut.android.mealplanner.domain.mapper.toEntity
import hu.bme.aut.android.mealplanner.domain.model.Food
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

}

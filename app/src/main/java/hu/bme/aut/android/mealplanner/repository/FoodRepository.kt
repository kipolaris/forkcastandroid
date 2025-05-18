package hu.bme.aut.android.mealplanner.repository

import hu.bme.aut.android.mealplanner.data.dao.FoodDao
import hu.bme.aut.android.mealplanner.data.dao.FoodIngredientCrossRefDao
import hu.bme.aut.android.mealplanner.data.dao.IngredientDao
import hu.bme.aut.android.mealplanner.data.dao.UnitOfMeasureDao
import hu.bme.aut.android.mealplanner.data.entity.FoodEntity
import hu.bme.aut.android.mealplanner.data.relation.FoodIngredientCrossRef
import hu.bme.aut.android.mealplanner.data.relation.FoodWithIngredientsRaw
import hu.bme.aut.android.mealplanner.data.relation.IngredientWithAmount
import hu.bme.aut.android.mealplanner.domain.mapper.*
import hu.bme.aut.android.mealplanner.domain.model.Food
import hu.bme.aut.android.mealplanner.domain.model.FoodIngredient
import hu.bme.aut.android.mealplanner.network.api.FoodApi

class FoodRepository(
    private val api: FoodApi,
    private val dao: FoodDao,
    private val ingredientDao: IngredientDao,
    private val unitOfMeasureDao: UnitOfMeasureDao,
    private val crossRefDao: FoodIngredientCrossRefDao
) {
    suspend fun getAll(): List<FoodEntity> = dao.getAll()

    suspend fun insertAll(items: List<FoodEntity>) = dao.insertAll(items)

    suspend fun deleteAll() = dao.deleteAll()

    suspend fun syncFoods() {
        try {
            val foodDtos = api.getAllFoods()
            for (dto in foodDtos) {
                val foodId = dao.insert(dto.toEntity())
                crossRefDao.deleteForFood(foodId)
                val crossRefs = dto.ingredients.map {
                    it.copy(foodId = foodId).toCrossRef()
                }
                crossRefDao.insertAll(crossRefs)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun insert(food: Food): Food {
        val response = try {
            api.addFood(food.toDto())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        val savedFood = if (response?.isSuccessful == true) {
            response.body()?.toDomain()
        } else {
            food.copy(id = dao.insert(food.toEntity()))
        }

        val localId = dao.insert(savedFood!!.toEntity())
        val crossRefs = savedFood.ingredients?.map {
            FoodIngredientCrossRef(
                foodId = localId,
                ingredientId = it.ingredient.id,
                amount = it.amount,
                unitId = it.unit.id
            )
        }
        if (crossRefs != null) {
            crossRefDao.insertAll(crossRefs)
        }
        return savedFood.copy(id = localId)
    }


    suspend fun delete(food: Food) {
        try {
            api.deleteFood(food.id)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        dao.delete(food.toEntity())
        crossRefDao.deleteForFood(food.id)
    }


    suspend fun update(food: Food) {
        try {
            api.updateFood(food.id, food.toDto())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        dao.update(food.toEntity())
        crossRefDao.deleteForFood(food.id)
        val crossRefs = food.ingredients?.map {
            FoodIngredientCrossRef(
                foodId = food.id,
                ingredientId = it.ingredient.id,
                amount = it.amount,
                unitId = it.unit.id
            )
        }
        if (crossRefs != null) {
            crossRefDao.insertAll(crossRefs)
        }
    }


    suspend fun getByIdWithIngredients(foodId: Long): Food {
        val foodEntity = dao.getById(foodId)
        val crossRefs = crossRefDao.getByFoodId(foodId)

        val ingredientIds = crossRefs.map { it.ingredientId }
        val unitIds = crossRefs.map { it.unitId }

        val ingredients = ingredientDao.getByIds(ingredientIds).associateBy { it.id }
        val units = unitOfMeasureDao.getByIds(unitIds).associateBy { it.id }

        val foodIngredients = crossRefs.mapNotNull { ref ->
            val ingredient = ingredients[ref.ingredientId]?.toDomain()
            val unit = units[ref.unitId]?.toDomain()

            if (ingredient != null && unit != null) {
                FoodIngredient(ingredient = ingredient, amount = ref.amount, unit = unit)
            } else null
        }

        return foodEntity.toDomain(ingredients = foodIngredients)
    }

    suspend fun getIngredientsWithAmount(foodId: Long): List<IngredientWithAmount> {
        return crossRefDao.getIngredientsWithAmount(foodId)
    }

    suspend fun getFood(foodId: Long): FoodWithIngredientsRaw {
        return dao.getFoodWithIngredients(foodId)
    }
}

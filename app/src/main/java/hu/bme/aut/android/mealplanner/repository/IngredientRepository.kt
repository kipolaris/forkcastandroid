package hu.bme.aut.android.mealplanner.repository

import hu.bme.aut.android.mealplanner.data.dao.IngredientDao
import hu.bme.aut.android.mealplanner.data.entity.IngredientEntity
import hu.bme.aut.android.mealplanner.domain.mapper.toDomain
import hu.bme.aut.android.mealplanner.domain.mapper.toDto
import hu.bme.aut.android.mealplanner.domain.mapper.toEntity
import hu.bme.aut.android.mealplanner.domain.model.Ingredient
import hu.bme.aut.android.mealplanner.network.api.IngredientApi

class IngredientRepository(
    private val api: IngredientApi,
    private val dao: IngredientDao
) {
    suspend fun getById(id: Long): Ingredient {
        return dao.getById(id).toDomain()
    }

    suspend fun getAll(): List<IngredientEntity> = dao.getAll()

    suspend fun insertAll(items: List<IngredientEntity>) = dao.insertAll(items)

    suspend fun deleteAll() = dao.deleteAll()

    suspend fun syncIngredients() {
        try {
            val ingredientDtos = api.getAllIngredients()
            val ingredientEntities = ingredientDtos.map { it.toEntity() }

            dao.deleteAll()
            dao.insertAll(ingredientEntities)
        } catch (e: Exception) {
            // TODO: handle failure case (maybe fallback to local cache)
        }
    }

    suspend fun update(ingredient: Ingredient) {
        try {
            api.updateIngredient(ingredient.id, ingredient.toDto())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        dao.updateIngredient(ingredient.toEntity())
    }

    suspend fun insert(ingredient: Ingredient): Ingredient {
        val response = try {
            api.addIngredient(ingredient.toDto())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        val savedIngredient = if (response?.isSuccessful == true) {
            response.body()?.toDomain()
        } else {
            ingredient.copy(id=dao.insert(ingredient.toEntity()))
        }

        val localId = dao.insert(savedIngredient!!.toEntity())

        return savedIngredient.copy(id = localId)
    }

    suspend fun delete(ingredient: Ingredient) {
        try {
            api.deleteIngredient(ingredient.id)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        dao.delete(ingredient.toEntity())
    }
}

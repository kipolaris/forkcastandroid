package hu.bme.aut.android.mealplanner.repository

import hu.bme.aut.android.mealplanner.data.dao.IngredientDao
import hu.bme.aut.android.mealplanner.data.entity.IngredientEntity
import hu.bme.aut.android.mealplanner.domain.mapper.toEntity
import hu.bme.aut.android.mealplanner.domain.model.Ingredient
import hu.bme.aut.android.mealplanner.network.api.IngredientApi

class IngredientRepository(
    private val api: IngredientApi,
    private val dao: IngredientDao
) {
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
        dao.updateIngredient(ingredient.toEntity())
    }

    suspend fun insert(ingredient: Ingredient): Long {
        return dao.insert(ingredient.toEntity())
    }

    suspend fun delete(ingredient: Ingredient) {
        dao.delete(ingredient.toEntity())
    }
}

package hu.bme.aut.android.mealplanner.repository

import hu.bme.aut.android.mealplanner.data.dao.IngredientDao
import hu.bme.aut.android.mealplanner.data.entity.IngredientEntity

class IngredientRepository(private val dao: IngredientDao) {
    suspend fun getAll(): List<IngredientEntity> = dao.getAll()
    suspend fun insertAll(items: List<IngredientEntity>) = dao.insertAll(items)
    suspend fun deleteAll() = dao.deleteAll()
}
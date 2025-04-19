package hu.bme.aut.android.mealplanner.repository

import hu.bme.aut.android.mealplanner.data.dao.FoodDao
import hu.bme.aut.android.mealplanner.data.entity.FoodEntity

class FoodRepository(private val dao: FoodDao) {
    suspend fun getAll(): List<FoodEntity> = dao.getAll()
    suspend fun insertAll(items: List<FoodEntity>) = dao.insertAll(items)
    suspend fun deleteAll() = dao.deleteAll()
}
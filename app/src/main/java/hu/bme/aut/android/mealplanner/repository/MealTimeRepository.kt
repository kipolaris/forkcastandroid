package hu.bme.aut.android.mealplanner.repository

import hu.bme.aut.android.mealplanner.data.dao.MealTimeDao
import hu.bme.aut.android.mealplanner.data.entity.MealTimeEntity

class MealTimeRepository(private val dao: MealTimeDao) {
    suspend fun getAll(): List<MealTimeEntity> = dao.getAll()
    suspend fun insertAll(items: List<MealTimeEntity>) = dao.insertAll(items)
    suspend fun deleteAll() = dao.deleteAll()
}
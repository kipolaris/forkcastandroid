package hu.bme.aut.android.mealplanner.repository

import hu.bme.aut.android.mealplanner.data.dao.DayDao
import hu.bme.aut.android.mealplanner.data.entity.DayEntity

class DayRepository(private val dao: DayDao) {
    suspend fun getAll(): List<DayEntity> = dao.getAll()
    suspend fun insertAll(items: List<DayEntity>) = dao.insertAll(items)
    suspend fun deleteAll() = dao.deleteAll()
}
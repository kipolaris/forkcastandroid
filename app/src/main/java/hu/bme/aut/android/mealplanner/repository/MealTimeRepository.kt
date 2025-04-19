package hu.bme.aut.android.mealplanner.repository

import hu.bme.aut.android.mealplanner.data.dao.MealTimeDao
import hu.bme.aut.android.mealplanner.data.entity.MealTimeEntity
import hu.bme.aut.android.mealplanner.domain.mapper.toEntity
import hu.bme.aut.android.mealplanner.network.api.MealTimeApi

class MealTimeRepository(
    private val api: MealTimeApi,
    private val dao: MealTimeDao
) {
    suspend fun getAll(): List<MealTimeEntity> = dao.getAll()

    suspend fun insertAll(items: List<MealTimeEntity>) = dao.insertAll(items)

    suspend fun deleteAll() = dao.deleteAll()

    suspend fun syncMealTimes() {
        try {
            val timeDtos = api.getAll()
            val timeEntities = timeDtos.map { it.toEntity() }
            dao.deleteAll()
            dao.insertAll(timeEntities)
        } catch (e: Exception) {
            // TODO fallback to cached data
        }
    }
}

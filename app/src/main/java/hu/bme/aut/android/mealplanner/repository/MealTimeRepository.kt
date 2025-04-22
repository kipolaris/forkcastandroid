package hu.bme.aut.android.mealplanner.repository

import hu.bme.aut.android.mealplanner.data.dao.MealTimeDao
import hu.bme.aut.android.mealplanner.data.entity.MealTimeEntity
import hu.bme.aut.android.mealplanner.domain.mapper.toEntity
import hu.bme.aut.android.mealplanner.domain.model.MealTime
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

    suspend fun initializeDefaultMealTimes() {
        if (dao.getAll().isEmpty()) {
            val defaults = listOf(
                MealTimeEntity(id = 1, name = "Breakfast", order = 1),
                MealTimeEntity(id = 2, name = "Lunch", order = 2),
                MealTimeEntity(id = 3, name = "Dinner", order = 3)
            )
            dao.insertAll(defaults)
        }
    }

    suspend fun update(mealTime: MealTime) {
        dao.updateMealTime(mealTime.toEntity())
    }

    suspend fun insert(mealTime: MealTime): Long {
        return dao.insert(mealTime.toEntity())
    }

    suspend fun delete(mealTime: MealTime) {
        dao.delete(mealTime.toEntity())
    }
}

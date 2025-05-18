package hu.bme.aut.android.mealplanner.repository

import hu.bme.aut.android.mealplanner.data.dao.MealTimeDao
import hu.bme.aut.android.mealplanner.data.entity.MealTimeEntity
import hu.bme.aut.android.mealplanner.domain.mapper.toDomain
import hu.bme.aut.android.mealplanner.domain.mapper.toDto
import hu.bme.aut.android.mealplanner.domain.mapper.toEntity
import hu.bme.aut.android.mealplanner.domain.model.MealTime
import hu.bme.aut.android.mealplanner.network.api.MealTimeApi
import hu.bme.aut.android.mealplanner.network.dto.ReorderRequestDto

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
        try {
            api.updateMealTime(mealTime.id, mealTime.toDto())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        dao.updateMealTime(mealTime.toEntity())
    }

    suspend fun insert(mealTime: MealTime): MealTime {
        val response = try {
            api.addMealTime(mealTime.toDto())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        val savedMealTime = if (response?.isSuccessful == true) {
            response.body()?.toDomain()
        } else {
            mealTime.copy(id = dao.insert(mealTime.toEntity()))
        }

        val localId = dao.insert(savedMealTime!!.toEntity())
        return savedMealTime.copy(id = localId)
    }

    suspend fun delete(mealTime: MealTime) {
        try {
            api.deleteMealTime(mealTime.id)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        dao.delete(mealTime.toEntity())
    }

    suspend fun reorderMealTimes(id1: Long, id2: Long, mealPlanId: Long = 1L): Boolean {
        return try {
            val request = ReorderRequestDto(
                mealTimeId1 = id1,
                mealTimeId2 = id2,
                mealPlanId = mealPlanId
            )
            val response = api.reorder(request)
            if (response.isSuccessful) {
                syncMealTimes()
                true
            } else false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}

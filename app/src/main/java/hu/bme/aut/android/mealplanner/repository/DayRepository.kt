package hu.bme.aut.android.mealplanner.repository

import hu.bme.aut.android.mealplanner.data.dao.DayDao
import hu.bme.aut.android.mealplanner.data.entity.DayEntity
import hu.bme.aut.android.mealplanner.domain.mapper.toEntity
import hu.bme.aut.android.mealplanner.network.api.DayApi

class DayRepository(
    private val api: DayApi,
    private val dao: DayDao
) {
    suspend fun getAll(): List<DayEntity> = dao.getAll()

    suspend fun insertAll(items: List<DayEntity>) = dao.insertAll(items)

    suspend fun deleteAll() = dao.deleteAll()

    suspend fun syncDays() {
        try {
            val dayDtos = api.getAllDays()
            val dayEntities = dayDtos.map { it.toEntity() }
            dao.deleteAll()
            dao.insertAll(dayEntities)
        } catch (e: Exception) {
            // TODO fallback to cache data
        }
    }
}

package hu.bme.aut.android.mealplanner.repository

import hu.bme.aut.android.mealplanner.data.dao.DayDao
import hu.bme.aut.android.mealplanner.data.dao.MealDao
import hu.bme.aut.android.mealplanner.data.entity.DayEntity
import hu.bme.aut.android.mealplanner.data.entity.DayWithFullMeals
import hu.bme.aut.android.mealplanner.domain.mapper.toEntity
import hu.bme.aut.android.mealplanner.network.api.DayApi

class DayRepository(
    private val api: DayApi,
    private val dao: DayDao,
    private val mealDao: MealDao
) {
    suspend fun getAll(): List<DayEntity> = dao.getAll()

    suspend fun insertAll(items: List<DayEntity>) = dao.insertAll(items)

    suspend fun deleteAll() = dao.deleteAll()

    suspend fun getAllWithMeals() = dao.getAllWithMeals()

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

    suspend fun initializeDefaultDays() {
        if (dao.getAll().isEmpty()) {
            val defaults = listOf(
                DayEntity(1, "Monday"),
                DayEntity(2, "Tuesday"),
                DayEntity(3, "Wednesday"),
                DayEntity(4, "Thursday"),
                DayEntity(5, "Friday"),
                DayEntity(6, "Saturday"),
                DayEntity(7, "Sunday")
            )
            dao.insertAll(defaults)
        }
    }

    suspend fun getDayWithFullMeals(dayId: Long): DayWithFullMeals {
        val day = dao.getDayById(dayId)
        val meals = mealDao.getMealsWithFoodByDayId(dayId)
        return DayWithFullMeals(day, meals)
    }
}

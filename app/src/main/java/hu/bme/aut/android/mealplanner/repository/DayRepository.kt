package hu.bme.aut.android.mealplanner.repository

import hu.bme.aut.android.mealplanner.data.dao.DayDao
import hu.bme.aut.android.mealplanner.data.dao.FoodDao
import hu.bme.aut.android.mealplanner.data.dao.MealTimeDao
import hu.bme.aut.android.mealplanner.data.entity.DayEntity
import hu.bme.aut.android.mealplanner.data.relation.DayWithFullMeals
import hu.bme.aut.android.mealplanner.data.relation.MealWithFood
import hu.bme.aut.android.mealplanner.domain.mapper.toEntity
import hu.bme.aut.android.mealplanner.network.api.DayApi

class DayRepository(
    private val api: DayApi,
    private val dao: DayDao,
    private val foodDao: FoodDao,
    private val mealTimeDao: MealTimeDao
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

    suspend fun getAllWithFullMeals(): List<DayWithFullMeals> {
        val daysWithMeals = dao.getAllWithMeals()
        val result = mutableListOf<DayWithFullMeals>()

        for (dayWithMeals in daysWithMeals) {
            val mealWithFoods = dayWithMeals.meals.map { meal ->
                MealWithFood(
                    meal = meal,
                    food = meal.foodId?.let { foodDao.getById(it) },
                    mealTime = mealTimeDao.getById(meal.mealTimeId)
                )
            }

            result += DayWithFullMeals(
                day = dayWithMeals.day,
                meals = mealWithFoods
            )
        }

        return result
    }
}

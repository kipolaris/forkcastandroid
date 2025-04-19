package hu.bme.aut.android.mealplanner.repository

import hu.bme.aut.android.mealplanner.data.dao.DayDao
import hu.bme.aut.android.mealplanner.data.dao.MealDao
import hu.bme.aut.android.mealplanner.data.dao.MealTimeDao
import hu.bme.aut.android.mealplanner.domain.mapper.toEntity
import hu.bme.aut.android.mealplanner.network.api.MealPlanApi

class MealPlanRepository(
    private val api: MealPlanApi,
    private val dayDao: DayDao,
    private val mealDao: MealDao,
    private val mealTimeDao: MealTimeDao
) {
    suspend fun syncMealPlan() {
        try {
            val plan = api.getMealPlan()
            val dayEntities = plan.days.map { it.toEntity() }
            val mealEntities = plan.days.flatMap { it.meals.map { meal -> meal.toEntity() } }
            val mealTimeEntities = plan.mealTimes.map { it.toEntity() }

            dayDao.deleteAll()
            mealDao.deleteAll()
            mealTimeDao.deleteAll()

            dayDao.insertAll(dayEntities)
            mealDao.insertAll(mealEntities)
            mealTimeDao.insertAll(mealTimeEntities)
        } catch (e: Exception) {
            // TODO handle errors
        }
    }
}
package hu.bme.aut.android.mealplanner.repository

import hu.bme.aut.android.mealplanner.data.dao.DayDao
import hu.bme.aut.android.mealplanner.data.dao.MealDao
import hu.bme.aut.android.mealplanner.data.entity.MealEntity
import hu.bme.aut.android.mealplanner.data.relation.DayWithFullMeals
import hu.bme.aut.android.mealplanner.data.relation.MealWithFood
import hu.bme.aut.android.mealplanner.domain.mapper.*
import hu.bme.aut.android.mealplanner.domain.model.Meal
import hu.bme.aut.android.mealplanner.network.api.MealApi

class MealRepository(
    private val api: MealApi,
    private val mealDao: MealDao,
    private val dayDao: DayDao
) {

    suspend fun getMeals(): List<MealEntity> {
        return try {
            val remote = api.getAllMeals().map { it.toEntity() }
            mealDao.deleteAll()
            mealDao.insertAll(remote)
            remote
        } catch (e: Exception) {
            e.printStackTrace()
            mealDao.getAll() // fallback
        }
    }

    suspend fun getMealsForDay(dayId: Long): List<MealWithFood> {
        return mealDao.getMealsWithFoodByDayId(dayId)
    }

    suspend fun getDayWithFullMeals(dayId: Long): DayWithFullMeals {
        val day = dayDao.getDayById(dayId)
        val meals = mealDao.getMealsWithFoodByDayId(dayId)
        return DayWithFullMeals(day, meals)
    }

    suspend fun insertOrUpdate(mealWithFood: MealWithFood) {
        val meal = mealWithFood.meal
        val dto = mealWithFood.toDto()

        try {
            if (meal.id == 0L) {
                val response = api.addMeal(dto)
                if (response.isSuccessful) {
                    val saved = response.body()!!.toEntity()
                    mealDao.insert(saved)
                } else {
                    mealDao.insert(meal)
                }
            } else {
                val response = api.updateMeal(meal.id, dto)
                if (response.isSuccessful) {
                    mealDao.insert(meal)
                } else {
                    mealDao.insert(meal)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            mealDao.insert(meal)
        }
    }

    suspend fun insertOrUpdateFromDomain(meal: Meal) {
        val foodEntity = meal.food?.toEntity()
        val mealTimeEntity = meal.mealTime.toEntity()

        val mealWithFood = MealWithFood(
            meal = meal.toEntity(),
            food = foodEntity,
            mealTime = mealTimeEntity
        )

        insertOrUpdate(mealWithFood)
    }
}

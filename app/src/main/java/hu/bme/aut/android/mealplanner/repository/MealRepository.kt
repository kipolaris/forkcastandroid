package hu.bme.aut.android.mealplanner.repository

import hu.bme.aut.android.mealplanner.data.dao.DayDao
import hu.bme.aut.android.mealplanner.data.dao.MealDao
import hu.bme.aut.android.mealplanner.data.entity.DayWithFullMeals
import hu.bme.aut.android.mealplanner.data.entity.MealEntity
import hu.bme.aut.android.mealplanner.data.entity.MealWithFood
import hu.bme.aut.android.mealplanner.domain.mapper.toEntity
import hu.bme.aut.android.mealplanner.network.api.MealApi
import hu.bme.aut.android.mealplanner.network.dto.MealDto

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
            mealDao.getAll() // fallback
        }
    }

    suspend fun addMeal(meal: MealDto): Boolean {
        return try {
            api.addMeal(meal).isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updateMeal(meal: MealDto): Boolean {
        return try {
            api.updateMeal(meal.id ?: return false, meal).isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteMeal(id: Long): Boolean {
        return try {
            api.deleteMeal(id).isSuccessful
        } catch (e: Exception) {
            false
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

    suspend fun insertOrUpdate(meal: MealEntity) {
        mealDao.insert(meal)
    }

    suspend fun cleanUpInvalidMeals() {
        mealDao.deleteMealsWithMissingMealTime()
    }
}


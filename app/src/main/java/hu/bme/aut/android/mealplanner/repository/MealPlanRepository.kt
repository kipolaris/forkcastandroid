package hu.bme.aut.android.mealplanner.repository

import android.util.Log
import hu.bme.aut.android.mealplanner.data.dao.DayDao
import hu.bme.aut.android.mealplanner.data.dao.FoodDao
import hu.bme.aut.android.mealplanner.data.dao.IngredientDao
import hu.bme.aut.android.mealplanner.data.dao.MealDao
import hu.bme.aut.android.mealplanner.data.dao.MealTimeDao
import hu.bme.aut.android.mealplanner.domain.mapper.toEntity
import hu.bme.aut.android.mealplanner.network.api.MealPlanApi
import hu.bme.aut.android.mealplanner.network.dto.DayDto
import hu.bme.aut.android.mealplanner.network.dto.MealPlanDto

class MealPlanRepository(
    private val api: MealPlanApi,
    private val dayDao: DayDao,
    private val mealDao: MealDao,
    private val mealTimeDao: MealTimeDao,
    private val foodDao: FoodDao,
    private val foodRepository: FoodRepository,
    private val ingredientDao: IngredientDao,
    private val ingredientRepository: IngredientRepository
) {
    suspend fun syncMealPlan() {
        try {
            val plan = api.getMealPlan()

            ingredientRepository.syncIngredients()
            val globalIngredients = ingredientDao.getAll()

            foodRepository.syncFoods()
            val globalFoods = foodDao.getAll()

            val dayEntities = plan.days.map { it.toEntity() }
            val mealEntities = plan.days.flatMap { it.meals.map { it.toEntity() } }
            val mealTimeEntities = plan.mealTimes.map { it.toEntity() }

            val ingredientIdsFromFoods = plan.days
                .flatMap { it.meals }
                .flatMap { it.food?.ingredients ?: emptyList() }
                .map { it.ingredientId }

            val ingredientsFromFoods = ingredientDao.getByIds(ingredientIdsFromFoods)
            val allIngredients = (globalIngredients + ingredientsFromFoods).distinctBy { it.id }

            val foodsFromMeals = plan.days
                .flatMap { it.meals.mapNotNull { it.food?.toEntity() } }
            val allFoods = (globalFoods + foodsFromMeals).distinctBy { it.id }

            mealDao.deleteAll()
            dayDao.deleteAll()
            mealTimeDao.deleteAll()
            foodDao.deleteAll()
            ingredientDao.deleteAll()

            ingredientDao.insertAll(allIngredients)
            foodDao.insertAll(allFoods)
            mealTimeDao.insertAll(mealTimeEntities.distinctBy { it.id })
            dayDao.insertAll(dayEntities)
            mealDao.insertAll(mealEntities)

            Log.d("SyncCheck", "syncMealPlan success")
        } catch (e: Exception) {
            Log.e("SyncCheck", "syncMealPlan failed", e)
        }
    }

    suspend fun updateMealPlan(mealPlan: MealPlanDto) {
        try {
            api.updateMealPlan(mealPlan)
        } catch (e: Exception) {
            Log.e("MealPlanRepository", "Failed to update MealPlan", e)
        }
    }

    suspend fun resetDay(dayDto: DayDto) {
        try {
            api.resetDay(dayDto)
        } catch (e: Exception) {
            Log.e("MealPlanRepository", "Failed to reset day", e)
        }
    }
}
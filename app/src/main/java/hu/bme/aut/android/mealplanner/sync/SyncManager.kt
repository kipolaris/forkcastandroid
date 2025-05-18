package hu.bme.aut.android.mealplanner.sync

import android.util.Log
import hu.bme.aut.android.mealplanner.repository.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncManager @Inject constructor(
    private val mealPlanRepository: MealPlanRepository,
    private val foodRepository: FoodRepository,
    private val ingredientRepository: IngredientRepository,
    private val dayRepository: DayRepository,
    private val mealTimeRepository: MealTimeRepository,
) {
    suspend fun syncAll() {
        try {
            foodRepository.syncFoods()
            ingredientRepository.syncIngredients()
            mealTimeRepository.syncMealTimes()
            dayRepository.syncDays()
            mealPlanRepository.syncMealPlan()
            Log.d("SyncManager", "Sync completed successfully")
        } catch (e: Exception) {
            Log.e("SyncManager", "Sync failed", e)
        }
    }
}

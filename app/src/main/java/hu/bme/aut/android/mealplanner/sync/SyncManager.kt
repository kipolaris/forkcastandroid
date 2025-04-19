package hu.bme.aut.android.mealplanner.sync

import android.util.Log
import hu.bme.aut.android.mealplanner.repository.FoodRepository
import hu.bme.aut.android.mealplanner.repository.IngredientRepository
import hu.bme.aut.android.mealplanner.repository.MealPlanRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncManager @Inject constructor(
    private val mealPlanRepository: MealPlanRepository,
    private val foodRepository: FoodRepository,
    private val ingredientRepository: IngredientRepository
) {
    suspend fun syncAll() {
        try {
            mealPlanRepository.syncMealPlan()
            foodRepository.syncFoods()
            ingredientRepository.syncIngredients()
            Log.d("SyncManager", "Sync completed successfully")
        } catch (e: Exception) {
            Log.e("SyncManager", "Sync failed", e)
        }
    }
}
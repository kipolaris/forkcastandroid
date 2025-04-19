package hu.bme.aut.android.mealplanner.repository

import hu.bme.aut.android.mealplanner.data.dao.MealDao
import hu.bme.aut.android.mealplanner.data.entity.MealEntity
import hu.bme.aut.android.mealplanner.domain.mapper.toEntity
import hu.bme.aut.android.mealplanner.network.api.MealApi
import hu.bme.aut.android.mealplanner.network.dto.MealDto
import retrofit2.Response

class MealRepository(
    private val api: MealApi,
    private val dao: MealDao
) {

    suspend fun getMeals(): List<MealEntity> {
        return try {
            val remote = api.getAllMeals().map { it.toEntity() }
            dao.deleteAll()
            dao.insertAll(remote)
            remote
        } catch (e: Exception) {
            dao.getAll() // fallback
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
}

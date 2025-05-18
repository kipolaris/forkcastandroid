package hu.bme.aut.android.mealplanner.network.api

import hu.bme.aut.android.mealplanner.network.dto.MealPlanDto
import retrofit2.http.GET

interface MealPlanApi {
    @GET("/api/meal-plan")
    suspend fun getMealPlan(): MealPlanDto
}
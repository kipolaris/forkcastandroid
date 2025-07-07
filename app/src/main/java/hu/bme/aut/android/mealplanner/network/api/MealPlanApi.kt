package hu.bme.aut.android.mealplanner.network.api

import hu.bme.aut.android.mealplanner.network.dto.DayDto
import hu.bme.aut.android.mealplanner.network.dto.MealPlanDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface MealPlanApi {
    @GET("/api/meal-plan")
    suspend fun getMealPlan(): MealPlanDto

    @PUT("/api/meal-plan")
    suspend fun updateMealPlan(@Body mealPlanDto: MealPlanDto): MealPlanDto

    @POST("api/meal-plan/reset-day")
    suspend fun resetDay(@Body day: DayDto): MealPlanDto
}
package hu.bme.aut.android.mealplanner.network.api

import hu.bme.aut.android.mealplanner.network.dto.MealDto
import retrofit2.Response
import retrofit2.http.*

interface MealApi {

    @GET("/api/meals")
    suspend fun getAllMeals(): List<MealDto>

    @POST("/api/meals")
    suspend fun addMeal(@Body meal: MealDto): Response<MealDto>

    @PUT("/api/meals/{id}")
    suspend fun updateMeal(
        @Path("id") id: Long,
        @Body meal: MealDto
    ): Response<MealDto>

    @DELETE("/api/meals/{id}")
    suspend fun deleteMeal(@Path("id") id: Long): Response<Unit>
}

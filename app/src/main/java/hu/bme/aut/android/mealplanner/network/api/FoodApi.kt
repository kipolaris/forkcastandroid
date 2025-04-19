package hu.bme.aut.android.mealplanner.network.api

import hu.bme.aut.android.mealplanner.network.dto.FoodDto
import retrofit2.Response
import retrofit2.http.*

interface FoodApi {

    @GET("/api/foods")
    suspend fun getAllFoods(): List<FoodDto>

    @POST("/api/foods")
    suspend fun addFood(@Body food: FoodDto): Response<Unit>

    @PUT("/api/foods/{id}")
    suspend fun updateFood(
        @Path("id") id: Long,
        @Body food: FoodDto
    ): Response<Unit>

    @DELETE("/api/foods/{id}")
    suspend fun deleteFood(@Path("id") id: Long): Response<Unit>
}

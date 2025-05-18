package hu.bme.aut.android.mealplanner.network.api

import hu.bme.aut.android.mealplanner.network.dto.FoodDto
import hu.bme.aut.android.mealplanner.network.dto.FoodIngredientRequestDto
import retrofit2.Response
import retrofit2.http.*

interface FoodApi {

    @GET("/api/foods")
    suspend fun getAllFoods(): List<FoodDto>

    @GET("/api/foods/{id}")
    suspend fun getFoodById(@Path("id") id: Long): Response<FoodDto>

    @POST("/api/foods")
    suspend fun addFood(@Body food: FoodDto): Response<FoodDto>

    @PUT("/api/foods/{id}")
    suspend fun updateFood(
        @Path("id") id: Long,
        @Body food: FoodDto
    ): Response<FoodDto>

    @DELETE("/api/foods/{id}")
    suspend fun deleteFood(@Path("id") id: Long): Response<Unit>

    @POST("/api/foods/{id}/ingredients")
    suspend fun addIngredientToFood(
        @Path("id") id: Long,
        @Body foodIngredient: FoodIngredientRequestDto
    ): Response<FoodDto>

    @PUT("/api/foods/{id}/ingredients")
    suspend fun updateIngredientInFood(
        @Path("id") id: Long,
        @Body foodIngredient: FoodIngredientRequestDto
    ): Response<FoodDto>

    @DELETE("/api/foods/{id}/ingredients")
    suspend fun deleteIngredientFromFood(
        @Path("id") id: Long,
        @Body foodIngredient: FoodIngredientRequestDto
    ): Response<Unit>
}

package hu.bme.aut.android.mealplanner.network.api

import hu.bme.aut.android.mealplanner.network.dto.MealTimeDto
import hu.bme.aut.android.mealplanner.network.dto.ReorderRequestDto
import retrofit2.Response
import retrofit2.http.*

interface MealTimeApi {

    @GET("/api/mealtimes")
    suspend fun getAll(): List<MealTimeDto>

    @POST("/api/mealtimes")
    suspend fun addMealTime(@Body mealTime: MealTimeDto): Response<Unit>

    @PUT("/api/mealtimes/{id}")
    suspend fun updateMealTime(
        @Path("id") id: Long,
        @Body mealTime: MealTimeDto
    ): Response<Unit>

    @DELETE("/api/mealtimes/{id}")
    suspend fun deleteMealTime(@Path("id") id: Long): Response<Unit>

    @POST("/api/mealtimes/reorder")
    suspend fun reorder(@Body request: ReorderRequestDto): Response<Unit>
}

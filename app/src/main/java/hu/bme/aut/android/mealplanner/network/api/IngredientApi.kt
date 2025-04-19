package hu.bme.aut.android.mealplanner.network.api

import hu.bme.aut.android.mealplanner.network.dto.IngredientDto
import retrofit2.Response
import retrofit2.http.*

interface IngredientApi {

    @GET("/api/ingredients")
    suspend fun getAllIngredients(): List<IngredientDto>

    @POST("/api/ingredients")
    suspend fun addIngredient(@Body ingredient: IngredientDto): Response<Unit>

    @PUT("/api/ingredients/{id}")
    suspend fun updateIngredient(
        @Path("id") id: Long,
        @Body ingredient: IngredientDto
    ): Response<Unit>

    @DELETE("/api/ingredients/{id}")
    suspend fun deleteIngredient(@Path("id") id: Long): Response<Unit>
}

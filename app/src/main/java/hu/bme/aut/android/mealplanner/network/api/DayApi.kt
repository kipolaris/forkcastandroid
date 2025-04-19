package hu.bme.aut.android.mealplanner.network.api

import hu.bme.aut.android.mealplanner.network.dto.DayDto
import retrofit2.Response
import retrofit2.http.*

interface DayApi {

    @GET("/api/days")
    suspend fun getAllDays(): List<DayDto>

    @POST("/api/days")
    suspend fun addDay(@Body day: DayDto): Response<Unit>

    @PUT("/api/days/{id}")
    suspend fun updateDay(
        @Path("id") id: Long,
        @Body day: DayDto
    ): Response<Unit>

    @DELETE("/api/days/{id}")
    suspend fun deleteDay(@Path("id") id: Long): Response<Unit>
}

package hu.bme.aut.android.mealplanner.network.api

import hu.bme.aut.android.mealplanner.network.dto.DayDto
import retrofit2.Response
import retrofit2.http.*

interface DayApi {
    @GET("/api/days")
    suspend fun getAllDays(): List<DayDto>
}

package hu.bme.aut.android.mealplanner.network.dto

data class DayDto(
    val id: Long,
    val name: String,
    val meals: List<MealDto>
)
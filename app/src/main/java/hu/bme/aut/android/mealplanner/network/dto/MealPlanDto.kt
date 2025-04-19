package hu.bme.aut.android.mealplanner.network.dto

data class MealPlanDto(
    val days: List<DayDto>,
    val mealTimes: List<MealTimeDto>
)
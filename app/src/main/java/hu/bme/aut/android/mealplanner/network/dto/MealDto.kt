package hu.bme.aut.android.mealplanner.network.dto

data class MealDto(
    val id: Long?,
    val foodId: Long?,
    val mealTimeId: Long,
    val dayId: Long
)


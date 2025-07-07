package hu.bme.aut.android.mealplanner.network.dto

data class MealDto(
    val id: Long?,
    val food: FoodDto?,
    val mealTime: MealTimeDto,
    val dayId: Long
)


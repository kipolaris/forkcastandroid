package hu.bme.aut.android.mealplanner.domain.model

data class Meal(
    val id: Long,
    val food: Food?,
    val mealTime: MealTime,
    val dayId: Long
)
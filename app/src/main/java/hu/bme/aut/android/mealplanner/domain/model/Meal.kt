package hu.bme.aut.android.mealplanner.domain.model

data class Meal(
    val id: Long,
    val foodId: Long?,
    val mealTimeId: Long,
    val dayId: Long
)
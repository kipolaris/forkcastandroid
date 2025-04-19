package hu.bme.aut.android.mealplanner.domain.model

data class MealPlan(
    val days: List<Day>,
    val mealTimes: List<MealTime>
)

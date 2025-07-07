package hu.bme.aut.android.mealplanner.domain.model

data class Day(
    val id: Long,
    val name: String,
    val meals: List<Meal>
)
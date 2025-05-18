package hu.bme.aut.android.mealplanner.domain.model

data class UnitOfMeasure(
    val id: Long,
    val name: String,
    val abbreviation: String,
    val type: String,
    val multiplierToBase: Double
)


package hu.bme.aut.android.mealplanner.network.dto

data class UnitOfMeasureDto(
    val id: Long,
    val name: String,
    val abbreviation: String,
    val type: String,
    val multiplierToBase: Double
)
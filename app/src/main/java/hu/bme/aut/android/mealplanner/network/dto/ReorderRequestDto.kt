package hu.bme.aut.android.mealplanner.network.dto

data class ReorderRequestDto(
    val mealTimeIds: List<Long>
)
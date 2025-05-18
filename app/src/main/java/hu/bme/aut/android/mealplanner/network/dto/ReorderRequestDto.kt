package hu.bme.aut.android.mealplanner.network.dto

data class ReorderRequestDto(
    val mealTimeId1: Long,
    val mealTimeId2: Long,
    val mealPlanId: Long
)

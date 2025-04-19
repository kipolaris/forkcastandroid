package hu.bme.aut.android.mealplanner.data.entity

@Entity(tableName = "meals")
data class MealEntity(
    @PrimaryKey val id: Long,
    val foodId: Long?,
    val mealTimeId: Long,
    val dayId: Long
)

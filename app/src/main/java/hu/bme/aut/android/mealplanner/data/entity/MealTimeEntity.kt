package hu.bme.aut.android.mealplanner.data.entity

@Entity(tableName = "meal_times")
class MealTimeEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val order: Int
)
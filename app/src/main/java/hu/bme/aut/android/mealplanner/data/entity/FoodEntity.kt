package hu.bme.aut.android.mealplanner.data.entity

@Entity(tableName = "foods")
data class FoodEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val description: String?
)
package hu.bme.aut.android.mealplanner.data.entity

@Entity(tableName = "days")
data class DayEntity(
    @PrimaryKey val id: Long,
    val name: String
)
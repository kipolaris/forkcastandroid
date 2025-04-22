package hu.bme.aut.android.mealplanner.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class MealEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val foodId: Long?,
    val mealTimeId: Long,
    val dayId: Long
)


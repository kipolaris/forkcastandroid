package hu.bme.aut.android.mealplanner.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mealtimes")
class MealTimeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val order: Int
)
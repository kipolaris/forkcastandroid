package hu.bme.aut.android.mealplanner.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "meals",
    foreignKeys = [
        ForeignKey(
            entity = MealTimeEntity::class,
            parentColumns = ["id"],
            childColumns = ["mealTimeId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = DayEntity::class,
            parentColumns = ["id"],
            childColumns = ["dayId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = FoodEntity::class,
            parentColumns = ["id"],
            childColumns = ["foodId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class MealEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val foodId: Long?,
    val mealTimeId: Long,
    val dayId: Long
)



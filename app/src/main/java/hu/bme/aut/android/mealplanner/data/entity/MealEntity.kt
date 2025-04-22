package hu.bme.aut.android.mealplanner.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "meals",
    indices = [
        Index(value = ["mealTimeId"]),
        Index(value = ["dayId"]),
        Index(value = ["foodId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = MealTimeEntity::class,
            parentColumns = ["id"],
            childColumns = ["mealTimeId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = FoodEntity::class,
            parentColumns = ["id"],
            childColumns = ["foodId"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = DayEntity::class,
            parentColumns = ["id"],
            childColumns = ["dayId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MealEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val foodId: Long?,
    val mealTimeId: Long,
    val dayId: Long
)



package hu.bme.aut.android.mealplanner.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "units")
data class UnitOfMeasureEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val abbreviation: String,
    val type: String,
    val multiplierToBase: Double
)

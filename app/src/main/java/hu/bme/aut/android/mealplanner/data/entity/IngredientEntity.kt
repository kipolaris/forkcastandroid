package hu.bme.aut.android.mealplanner.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class IngredientEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val quantity: String
)
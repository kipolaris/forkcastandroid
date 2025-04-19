package hu.bme.aut.android.mealplanner.data.entity

@Entity(tableName = "ingredients")
data class IngredientEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val quantity: String,
    val foodId: Long
)
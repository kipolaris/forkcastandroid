package hu.bme.aut.android.mealplanner.data.relation

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation
import hu.bme.aut.android.mealplanner.data.entity.IngredientEntity
import hu.bme.aut.android.mealplanner.data.entity.UnitOfMeasureEntity

data class IngredientWithAmount(
    @Embedded(prefix = "ingredient_")
    val ingredient: IngredientEntity,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "unitId")
    val unitId: Long,

    @Relation(
        parentColumn = "unitId",
        entityColumn = "id"
    )
    val unit: UnitOfMeasureEntity
)




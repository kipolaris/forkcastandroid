package hu.bme.aut.android.mealplanner.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import hu.bme.aut.android.mealplanner.data.relation.FoodIngredientCrossRef
import hu.bme.aut.android.mealplanner.data.relation.IngredientWithAmount

@Dao
interface FoodIngredientCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(crossRefs: List<FoodIngredientCrossRef>)

    @Query("DELETE FROM food_ingredient_cross_ref")
    suspend fun deleteAll()

    @Query("DELETE FROM food_ingredient_cross_ref WHERE foodId = :foodId")
    suspend fun deleteForFood(foodId: Long)

    @Query("SELECT * FROM food_ingredient_cross_ref WHERE foodId = :foodId")
    suspend fun getByFoodId(foodId: Long): List<FoodIngredientCrossRef>

    @Transaction
    @Query("""
    SELECT 
        i.id AS ingredient_id,
        i.name AS ingredient_name,
        r.amount,
        r.unitId,
        u.id AS unit_id,
        u.name AS unit_name,
        u.abbreviation,
        u.type,
        u.multiplierToBase
    FROM ingredients i
    INNER JOIN food_ingredient_cross_ref r ON i.id = r.ingredientId
    INNER JOIN units u ON r.unitId = u.id
    WHERE r.foodId = :foodId
""")
    suspend fun getIngredientsWithAmount(foodId: Long): List<IngredientWithAmount>
}

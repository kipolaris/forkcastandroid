package hu.bme.aut.android.mealplanner.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.bme.aut.android.mealplanner.data.relation.FoodIngredientCrossRef

@Dao
interface FoodIngredientCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(crossRefs: List<FoodIngredientCrossRef>)

    @Query("DELETE FROM food_ingredient_cross_ref")
    suspend fun deleteAll()

    @Query("DELETE FROM food_ingredient_cross_ref WHERE foodId = :foodId")
    suspend fun deleteForFood(foodId: Long)
}
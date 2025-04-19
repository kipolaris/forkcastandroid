package hu.bme.aut.android.mealplanner.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.bme.aut.android.mealplanner.data.entity.FoodIngredientCrossRef

@Dao
interface FoodIngredientCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(crossRefs: List<FoodIngredientCrossRef>)

    @Query("DELETE FROM food_ingredient_cross_ref")
    suspend fun deleteAll()
}
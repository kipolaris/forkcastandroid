package hu.bme.aut.android.mealplanner.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.bme.aut.android.mealplanner.data.entity.MealTimeEntity

@Dao
interface MealTimeDao {
    @Query("SELECT * FROM mealtimes")
    suspend fun getAll(): List<MealTimeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<MealTimeEntity>)

    @Query("DELETE FROM mealtimes")
    suspend fun deleteAll()
}
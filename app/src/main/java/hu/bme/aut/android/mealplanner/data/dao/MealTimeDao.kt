package hu.bme.aut.android.mealplanner.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import hu.bme.aut.android.mealplanner.data.entity.MealTimeEntity

@Dao
interface MealTimeDao {
    @Query("SELECT * FROM mealtimes")
    suspend fun getAll(): List<MealTimeEntity>

    @Query("SELECT * FROM mealtimes WHERE id = :mealTimeId")
    suspend fun getById(mealTimeId: Long): MealTimeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<MealTimeEntity>)

    @Query("DELETE FROM mealtimes")
    suspend fun deleteAll()

    @Update
    suspend fun updateMealTime(mealTime: MealTimeEntity)

    @Insert
    suspend fun insert(mealTime: MealTimeEntity): Long

    @Delete
    suspend fun delete(mealTime: MealTimeEntity)
}
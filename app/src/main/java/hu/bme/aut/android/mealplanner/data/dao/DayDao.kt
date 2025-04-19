package hu.bme.aut.android.mealplanner.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.bme.aut.android.mealplanner.data.entity.DayEntity

@Dao
interface DayDao {
    @Query("SELECT * FROM days")
    suspend fun getAll(): List<DayEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<DayEntity>)
    @Query("DELETE FROM days") suspend fun deleteAll()
}
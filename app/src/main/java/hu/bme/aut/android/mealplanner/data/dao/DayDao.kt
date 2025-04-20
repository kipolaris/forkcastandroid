package hu.bme.aut.android.mealplanner.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import hu.bme.aut.android.mealplanner.data.entity.DayEntity
import hu.bme.aut.android.mealplanner.data.entity.DayWithFullMeals
import hu.bme.aut.android.mealplanner.data.entity.DayWithMeals

@Dao
interface DayDao {
    @Query("SELECT * FROM days")
    suspend fun getAll(): List<DayEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<DayEntity>)

    @Query("DELETE FROM days")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM days")
    suspend fun getAllWithMeals(): List<DayWithMeals>

    @Query("SELECT * FROM days WHERE id = :dayId LIMIT 1")
    suspend fun getDayById(dayId: Long): DayEntity
}
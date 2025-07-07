package hu.bme.aut.android.mealplanner.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.bme.aut.android.mealplanner.data.entity.UnitOfMeasureEntity

@Dao
interface UnitOfMeasureDao {
    @Query("SELECT * FROM units")
    suspend fun getAll(): List<UnitOfMeasureEntity>

    @Query("SELECT * FROM units WHERE id = :id")
    suspend fun getById(id: Long): UnitOfMeasureEntity?

    @Query("SELECT * FROM units WHERE id IN (:ids)")
    suspend fun getByIds(ids: List<Long>): List<UnitOfMeasureEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(units: List<UnitOfMeasureEntity>)
}

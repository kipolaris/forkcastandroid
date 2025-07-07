package hu.bme.aut.android.mealplanner.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import hu.bme.aut.android.mealplanner.data.entity.IngredientEntity

@Dao
interface IngredientDao {
    @Query("SELECT * FROM ingredients")
    suspend fun getAll(): List<IngredientEntity>

    @Query("SELECT * FROM ingredients WHERE id = :id")
    suspend fun getById(id: Long): IngredientEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<IngredientEntity>)

    @Query("DELETE FROM ingredients")
    suspend fun deleteAll()

    @Update
    suspend fun updateIngredient(ingredient: IngredientEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingredient: IngredientEntity): Long

    @Delete
    suspend fun delete(ingredient: IngredientEntity)

    @Query("SELECT * FROM ingredients WHERE id IN (:ids)")
    suspend fun getByIds(ids: List<Long>): List<IngredientEntity>
}

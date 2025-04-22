package hu.bme.aut.android.mealplanner.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import hu.bme.aut.android.mealplanner.data.entity.FoodEntity
import hu.bme.aut.android.mealplanner.data.entity.FoodWithIngredients
import hu.bme.aut.android.mealplanner.domain.model.Food

@Dao
interface FoodDao {
    @Query("SELECT * FROM foods")
    suspend fun getAll(): List<FoodEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<FoodEntity>)

    @Query("DELETE FROM foods")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM foods")
    suspend fun getAllWithIngredients(): List<FoodWithIngredients>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(food: FoodEntity): Long

    @Delete
    suspend fun delete(food: FoodEntity)
}
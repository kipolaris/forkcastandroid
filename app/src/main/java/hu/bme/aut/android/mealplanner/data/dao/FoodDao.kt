package hu.bme.aut.android.mealplanner.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import hu.bme.aut.android.mealplanner.data.entity.FoodEntity
import hu.bme.aut.android.mealplanner.data.relation.FoodWithIngredientsRaw

@Dao
interface FoodDao {
    @Query("SELECT * FROM foods")
    suspend fun getAll(): List<FoodEntity>

    @Query("SELECT * FROM foods WHERE id = :foodId")
    suspend fun getById(foodId: Long): FoodEntity

    @Transaction
    @Query("SELECT * FROM foods WHERE id = :foodId")
    suspend fun getFoodWithIngredients(foodId: Long): FoodWithIngredientsRaw

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(food: FoodEntity): Long

    @Update
    suspend fun update(food: FoodEntity)

    @Delete
    suspend fun delete(food: FoodEntity)

    @Query("DELETE FROM foods")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<FoodEntity>)
}

package hu.bme.aut.android.mealplanner.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import hu.bme.aut.android.mealplanner.data.entity.MealEntity
import hu.bme.aut.android.mealplanner.data.entity.MealWithFood

@Dao
interface MealDao {

    @Query("SELECT * FROM meals")
    suspend fun getAll(): List<MealEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(meals: List<MealEntity>)

    @Query("DELETE FROM meals")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM meals WHERE dayId = :dayId")
    suspend fun getMealsWithFoodByDayId(dayId: Long): List<MealWithFood>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meal: MealEntity)
}

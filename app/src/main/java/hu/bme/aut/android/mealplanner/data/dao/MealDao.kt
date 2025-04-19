package hu.bme.aut.android.mealplanner.data.dao

@Dao
interface MealDao {

    @Query("SELECT * FROM meals")
    suspend fun getAll(): List<MealEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(meals: List<MealEntity>)

    @Query("DELETE FROM meals")
    suspend fun deleteAll()
}

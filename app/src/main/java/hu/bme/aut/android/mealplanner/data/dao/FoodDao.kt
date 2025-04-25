package hu.bme.aut.android.mealplanner.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import hu.bme.aut.android.mealplanner.data.entity.FoodEntity
import hu.bme.aut.android.mealplanner.data.relation.FoodWithIngredientRaw

@Dao
interface FoodDao {
    @Query("SELECT * FROM foods")
    suspend fun getAll(): List<FoodEntity>

    @Query("""
    SELECT 
        foods.id AS foodId,
        foods.name AS foodName,
        foods.description AS foodDescription,
        ingredients.id AS ingredientId,
        ingredients.name AS ingredientName,
        food_ingredient_cross_ref.quantity AS quantityInCrossRef
    FROM foods
    LEFT JOIN food_ingredient_cross_ref ON foods.id = food_ingredient_cross_ref.foodId
    LEFT JOIN ingredients ON ingredients.id = food_ingredient_cross_ref.ingredientId
    WHERE foods.id = :foodId
""")
    suspend fun getFoodWithIngredientsRaw(foodId: Long): List<FoodWithIngredientRaw>

    @Query("SELECT * FROM foods WHERE id = :foodId")
    suspend fun getById(foodId: Long): FoodEntity

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

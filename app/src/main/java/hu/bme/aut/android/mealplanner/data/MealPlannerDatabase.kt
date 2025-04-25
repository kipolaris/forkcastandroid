package hu.bme.aut.android.mealplanner.data

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.bme.aut.android.mealplanner.data.dao.DayDao
import hu.bme.aut.android.mealplanner.data.dao.FoodDao
import hu.bme.aut.android.mealplanner.data.dao.IngredientDao
import hu.bme.aut.android.mealplanner.data.dao.MealDao
import hu.bme.aut.android.mealplanner.data.dao.MealTimeDao
import hu.bme.aut.android.mealplanner.data.entity.DayEntity
import hu.bme.aut.android.mealplanner.data.entity.FoodEntity
import hu.bme.aut.android.mealplanner.data.entity.IngredientEntity
import hu.bme.aut.android.mealplanner.data.entity.MealEntity
import hu.bme.aut.android.mealplanner.data.entity.MealTimeEntity

import hu.bme.aut.android.mealplanner.data.dao.FoodIngredientCrossRefDao
import hu.bme.aut.android.mealplanner.data.relation.FoodIngredientCrossRef

@Database(
    entities = [
        DayEntity::class,
        FoodEntity::class,
        IngredientEntity::class,
        MealEntity::class,
        MealTimeEntity::class,
        FoodIngredientCrossRef::class
    ],
    version = 5,
    exportSchema = false
)
abstract class MealPlannerDatabase : RoomDatabase() {

    abstract fun dayDao(): DayDao
    abstract fun foodDao(): FoodDao
    abstract fun ingredientDao(): IngredientDao
    abstract fun mealDao(): MealDao
    abstract fun mealTimeDao(): MealTimeDao
    abstract fun foodIngredientCrossRefDao(): FoodIngredientCrossRefDao
}



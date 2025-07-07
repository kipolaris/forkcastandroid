package hu.bme.aut.android.mealplanner.repository

import hu.bme.aut.android.mealplanner.data.dao.UnitOfMeasureDao
import hu.bme.aut.android.mealplanner.data.entity.UnitOfMeasureEntity

class UnitOfMeasureRepository(
    private val dao: UnitOfMeasureDao
) {
    suspend fun getAll(): List<UnitOfMeasureEntity> = dao.getAll()

    suspend fun initializeUnitsOfMeasure(): List<UnitOfMeasureEntity> {
        val predefinedUnits = listOf(
            // Mass (base: gram)
            UnitOfMeasureEntity(id=1, name = "milligram", abbreviation = "mg", type = "mass", multiplierToBase = 0.001),
            UnitOfMeasureEntity(id=2, name = "gram", abbreviation = "g", type = "mass", multiplierToBase = 1.0),
            UnitOfMeasureEntity(id=3, name = "decagram", abbreviation = "dg", type = "mass", multiplierToBase = 10.0),
            UnitOfMeasureEntity(id=4, name = "kilogram", abbreviation = "kg", type = "mass", multiplierToBase = 1000.0),

            // Volume (base: milliliter)
            UnitOfMeasureEntity(id=5, name = "milliliter", abbreviation = "ml", type = "volume", multiplierToBase = 1.0),
            UnitOfMeasureEntity(id=6, name = "deciliter", abbreviation = "dl", type = "volume", multiplierToBase = 100.0),
            UnitOfMeasureEntity(id=7, name = "liter", abbreviation = "l", type = "volume", multiplierToBase = 1000.0),

            // Custom (non-convertible)
            UnitOfMeasureEntity(id=8, name = "cup", abbreviation = "cup", type = "custom", multiplierToBase = 1.0),
            UnitOfMeasureEntity(id=9, name = "bag", abbreviation = "bag", type = "custom", multiplierToBase = 1.0),
            UnitOfMeasureEntity(id=10, name = "pack", abbreviation = "pack", type = "custom", multiplierToBase = 1.0),
            UnitOfMeasureEntity(id=11, name = "teaspoon", abbreviation = "tsp", type = "custom", multiplierToBase = 1.0),
            UnitOfMeasureEntity(id=12, name = "tablespoon", abbreviation = "tbsp", type = "custom", multiplierToBase = 1.0),
            UnitOfMeasureEntity(id=13, name = "carton", abbreviation = "carton", type = "custom", multiplierToBase = 1.0),
            UnitOfMeasureEntity(id=14, name = "bottle", abbreviation = "bottle", type = "custom", multiplierToBase = 1.0),
        )

        dao.insertAll(predefinedUnits)
        return predefinedUnits
    }
}
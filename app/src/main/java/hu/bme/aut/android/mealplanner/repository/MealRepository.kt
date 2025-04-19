package hu.bme.aut.android.mealplanner.repository

class MealRepository(
    private val api: MealApi,
    private val dao: MealDao
) {
    suspend fun getMeals(): List<MealEntity> {
        return try {
            val remote = api.getAllMeals().map { it.toEntity() }
            dao.deleteAll()
            dao.insertAll(remote)
            remote
        } catch (e: Exception) {
            dao.getAll()
        }
    }
}

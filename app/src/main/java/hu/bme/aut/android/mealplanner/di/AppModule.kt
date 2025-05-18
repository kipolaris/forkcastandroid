package hu.bme.aut.android.mealplanner.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.mealplanner.data.MealPlannerDatabase
import hu.bme.aut.android.mealplanner.data.dao.*
import hu.bme.aut.android.mealplanner.network.api.DayApi
import hu.bme.aut.android.mealplanner.network.api.FoodApi
import hu.bme.aut.android.mealplanner.network.api.IngredientApi
import hu.bme.aut.android.mealplanner.network.api.MealApi
import hu.bme.aut.android.mealplanner.network.api.MealPlanApi
import hu.bme.aut.android.mealplanner.network.api.MealTimeApi
import hu.bme.aut.android.mealplanner.repository.DayRepository
import hu.bme.aut.android.mealplanner.repository.FoodRepository
import hu.bme.aut.android.mealplanner.repository.IngredientRepository
import hu.bme.aut.android.mealplanner.repository.MealPlanRepository
import hu.bme.aut.android.mealplanner.repository.MealRepository
import hu.bme.aut.android.mealplanner.repository.MealTimeRepository
import hu.bme.aut.android.mealplanner.repository.UnitOfMeasureRepository
import hu.bme.aut.android.mealplanner.util.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MealPlannerDatabase =
        Room.databaseBuilder(
            context,
            MealPlannerDatabase::class.java,
            "mealplanner.db"
        ).fallbackToDestructiveMigration().build()

    // Dao
    @Provides fun provideDayDao(db: MealPlannerDatabase): DayDao = db.dayDao()
    @Provides fun provideFoodDao(db: MealPlannerDatabase): FoodDao = db.foodDao()
    @Provides fun provideIngredientDao(db: MealPlannerDatabase): IngredientDao = db.ingredientDao()
    @Provides fun provideMealDao(db: MealPlannerDatabase): MealDao = db.mealDao()
    @Provides fun provideMealTimeDao(db: MealPlannerDatabase): MealTimeDao = db.mealTimeDao()
    @Provides fun provideFoodIngredientCrossRefDao(db: MealPlannerDatabase): FoodIngredientCrossRefDao = db.foodIngredientCrossRefDao()
    @Provides fun provideUnitOfMeasureDao(db: MealPlannerDatabase) = db.unitOfMeasureDao()


    //Retrofit
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    // Api
    @Provides
    fun provideMealApi(retrofit: Retrofit): MealApi = retrofit.create(MealApi::class.java)

    @Provides
    fun provideFoodApi(retrofit: Retrofit): FoodApi = retrofit.create(FoodApi::class.java)

    @Provides
    fun provideIngredientApi(retrofit: Retrofit): IngredientApi = retrofit.create(IngredientApi::class.java)

    @Provides
    fun provideDayApi(retrofit: Retrofit): DayApi = retrofit.create(DayApi::class.java)

    @Provides
    fun provideMealTimeApi(retrofit: Retrofit): MealTimeApi = retrofit.create(MealTimeApi::class.java)

    @Provides
    fun provideMealPlanApi(retrofit: Retrofit): MealPlanApi = retrofit.create(MealPlanApi::class.java)

    // Repository
    @Provides
    @Singleton
    fun provideMealPlanRepository(
        api: MealPlanApi,
        mealDao: MealDao,
        mealTimeDao: MealTimeDao,
        dayDao: DayDao,
        foodDao: FoodDao,
        foodRepository: FoodRepository,
        ingredientDao: IngredientDao,
        ingredientRepository: IngredientRepository
    ): MealPlanRepository = MealPlanRepository(api, dayDao, mealDao, mealTimeDao, foodDao, foodRepository, ingredientDao, ingredientRepository)

    @Provides
    @Singleton
    fun provideFoodRepository(
        api: FoodApi,
        foodDao: FoodDao,
        ingredientDao: IngredientDao,
        unitOfMeasureDao: UnitOfMeasureDao,
        crossRefDao: FoodIngredientCrossRefDao
    ): FoodRepository = FoodRepository(api, foodDao, ingredientDao, unitOfMeasureDao, crossRefDao)

    @Provides
    @Singleton
    fun provideIngredientRepository(
        api: IngredientApi,
        ingredientDao: IngredientDao
    ): IngredientRepository = IngredientRepository(api, ingredientDao)

    @Provides
    @Singleton
    fun provideDayRepository(
        api: DayApi,
        dayDao: DayDao,
        foodDao: FoodDao,
        mealTimeDao: MealTimeDao
    ): DayRepository = DayRepository(api, dayDao, foodDao, mealTimeDao)

    @Provides
    @Singleton
    fun provideMealTimeRepository(
        api: MealTimeApi,
        mealTimeDao: MealTimeDao
    ): MealTimeRepository = MealTimeRepository(api, mealTimeDao)

    @Provides
    @Singleton
    fun provideMealRepository(
        api: MealApi,
        mealDao: MealDao,
        dayDao: DayDao
    ): MealRepository = MealRepository(api, mealDao, dayDao)

    @Provides
    @Singleton
    fun provideUnitOfMeasureRepository(
        dao: UnitOfMeasureDao
    ): UnitOfMeasureRepository = UnitOfMeasureRepository(dao)
}

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

    @Provides fun provideDayDao(db: MealPlannerDatabase): DayDao = db.dayDao()
    @Provides fun provideFoodDao(db: MealPlannerDatabase): FoodDao = db.foodDao()
    @Provides fun provideIngredientDao(db: MealPlannerDatabase): IngredientDao = db.ingredientDao()
    @Provides fun provideMealDao(db: MealPlannerDatabase): MealDao = db.mealDao()
    @Provides fun provideMealTimeDao(db: MealPlannerDatabase): MealTimeDao = db.mealTimeDao()

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
            .baseUrl("http://10.0.2.2:8080") // ⚠️ Replace with your actual base URL
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    fun provideMealApi(retrofit: Retrofit): MealApi = retrofit.create(MealApi::class.java)

    @Provides
    fun provideFoodIngredientCrossRefDao(db: MealPlannerDatabase): FoodIngredientCrossRefDao = db.foodIngredientCrossRefDao()

    @Provides
    fun provideFoodApi(retrofit: Retrofit): FoodApi = retrofit.create(FoodApi::class.java)

    @Provides
    fun provideIngredientApi(retrofit: Retrofit): IngredientApi =
        retrofit.create(IngredientApi::class.java)

    @Provides
    fun provideDayApi(retrofit: Retrofit): DayApi =
        retrofit.create(DayApi::class.java)

}

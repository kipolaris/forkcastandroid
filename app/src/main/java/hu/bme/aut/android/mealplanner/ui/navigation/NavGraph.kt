package hu.bme.aut.android.mealplanner.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hu.bme.aut.android.mealplanner.ui.screens.MenuScreen
import hu.bme.aut.android.mealplanner.ui.screens.DayScreen
import hu.bme.aut.android.mealplanner.ui.screens.FoodScreen
import hu.bme.aut.android.mealplanner.ui.screens.FoodsScreen
import hu.bme.aut.android.mealplanner.ui.screens.IngredientsScreen
import hu.bme.aut.android.mealplanner.ui.screens.MealTimesScreen
import hu.bme.aut.android.mealplanner.viewmodel.MainViewModel

@Composable
fun NavGraph(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController = navController, startDestination = "menu") {
        composable("menu") {
            MenuScreen(navController, viewModel)
        }
        composable("dayPage/{dayIndex}") { backStackEntry ->
            val dayIndex = backStackEntry.arguments?.getString("dayIndex")?.toIntOrNull() ?: 0
            DayScreen(navController, dayIndex)
        }
        composable("mealTimes") {
            MealTimesScreen(navController)
        }
        composable("foods") {
            FoodsScreen(navController)
        }
        composable("ingredients") {
            IngredientsScreen(navController)
        }
        composable("food/{foodId}") {
            FoodScreen(navController)
        }

    }
}
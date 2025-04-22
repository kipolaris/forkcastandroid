package hu.bme.aut.android.mealplanner.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hu.bme.aut.android.mealplanner.ui.screens.MenuScreen
import hu.bme.aut.android.mealplanner.ui.screens.DayScreen
import hu.bme.aut.android.mealplanner.ui.screens.FoodsScreen
import hu.bme.aut.android.mealplanner.ui.screens.IngredientsScreen
import hu.bme.aut.android.mealplanner.ui.screens.MealTimesScreen

//import hu.bme.aut.android.mealplanner.ui.screens.MealTimePageScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "menu") {
        composable("menu") {
            MenuScreen(navController)
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
    }
}
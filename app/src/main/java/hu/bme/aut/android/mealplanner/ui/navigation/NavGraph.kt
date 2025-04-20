package hu.bme.aut.android.mealplanner.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hu.bme.aut.android.mealplanner.ui.screens.MenuScreen
import hu.bme.aut.android.mealplanner.ui.screens.DayPageScreen
//import hu.bme.aut.android.mealplanner.ui.screens.MealTimePageScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "menu") {
        composable("menu") {
            MenuScreen(navController)
        }
        composable("dayPage/{dayIndex}") { backStackEntry ->
            val dayIndex = backStackEntry.arguments?.getString("dayIndex")?.toIntOrNull() ?: 0
            DayPageScreen(navController, dayIndex)
        }
        /*composable("mealTimePage/{mealTimeIndex}") { backStackEntry ->
            val mealTimeIndex = backStackEntry.arguments?.getString("mealTimeIndex")?.toIntOrNull() ?: 0
            MealTimePageScreen(navController, mealTimeIndex)
        }*/
    }
}
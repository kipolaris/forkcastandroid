package hu.bme.aut.android.mealplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.mealplanner.ui.navigation.NavGraph
import hu.bme.aut.android.mealplanner.ui.theme.MealPlannerTheme
import hu.bme.aut.android.mealplanner.viewmodel.MainViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val isLoading by viewModel.isLoading.collectAsState()

            if (isLoading) {
                Surface {
                    CircularProgressIndicator()
                }
            } else {
                val navController = rememberNavController()
                MealPlannerTheme {
                    NavGraph(navController = navController)
                }
            }
        }
    }
}
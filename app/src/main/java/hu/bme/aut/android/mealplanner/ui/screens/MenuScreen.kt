package hu.bme.aut.android.mealplanner.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hu.bme.aut.android.mealplanner.R
import hu.bme.aut.android.mealplanner.ui.components.MenuButton
import hu.bme.aut.android.mealplanner.ui.components.ThemedBackground
import hu.bme.aut.android.mealplanner.ui.components.PageHeader
import hu.bme.aut.android.mealplanner.viewmodel.MainViewModel

@Composable
fun MenuScreen(navController: NavController, viewModel: MainViewModel) {
    ThemedBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            PageHeader(title = "Meal Planner")

            Spacer(modifier = Modifier.height(48.dp))

            val buttons = listOf(
                "Meal Plan" to "dayPage/0",
                "Foods" to "foods",
                "Ingredients" to "ingredients",
                "Meal times" to "mealtimes"
            )

            buttons.forEach { (label, route) ->
                MenuButton(
                    label = label,
                    onClick = { navController.navigate(route) },
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.moon),
                contentDescription = "Dark mode",
                modifier = Modifier
                    .clickable { viewModel.toggleTheme() }
                    .height(120.dp)
                    .width(120.dp)
                    .padding(horizontal = 32.dp)
            )
        }
    }
}

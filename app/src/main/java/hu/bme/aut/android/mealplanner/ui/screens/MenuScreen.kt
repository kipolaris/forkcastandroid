package hu.bme.aut.android.mealplanner.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hu.bme.aut.android.mealplanner.ui.components.MenuButton
import hu.bme.aut.android.mealplanner.ui.components.ThemedBackground
import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import hu.bme.aut.android.mealplanner.R
import hu.bme.aut.android.mealplanner.ui.components.PageHeader
import hu.bme.aut.android.mealplanner.ui.theme.LobsterFont

@Composable
fun MenuScreen(navController: NavController) {
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
        }
    }
}

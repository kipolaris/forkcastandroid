package hu.bme.aut.android.mealplanner.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hu.bme.aut.android.mealplanner.R
import hu.bme.aut.android.mealplanner.ui.components.ThemedBackground
import hu.bme.aut.android.mealplanner.ui.theme.LobsterFont

@Composable
fun MenuScreen(navController: NavController) {
    ThemedBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .padding(bottom = 48.dp)
                    .wrapContentSize()
            ) {
                Surface(
                    modifier = Modifier
                        .height(60.dp)
                        .width(280.dp),
                    color = Color.White
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.paper),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .matchParentSize()
                    )
                }

                Text(
                    text = "Meal Planner",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = LobsterFont,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFFFF78A8)
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )

                Image(
                    painter = painterResource(id = R.drawable.pinkflowertape),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .rotate(45f)
                        .offset(x = (-8).dp, y = (8).dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.pinkflowertape),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .rotate(45f)
                        .offset(x = (8).dp, y = (-8).dp)
                )
            }

            val buttons = listOf(
                "Meal Plan" to "dayPage/0",
                "Foods" to "foods",
                "Ingredients" to "ingredients",
                "Meal times" to "mealtimes"
            )

            buttons.forEach { (label, route) ->
                Box(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .size(width = 220.dp, height = 74.dp)
                        .clickable { navController.navigate(route) },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.whitetape),
                        contentDescription = null,
                        modifier = Modifier.matchParentSize()
                    )
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = LobsterFont,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFFFF78A8)
                        )
                    )
                }
            }
        }
    }
}

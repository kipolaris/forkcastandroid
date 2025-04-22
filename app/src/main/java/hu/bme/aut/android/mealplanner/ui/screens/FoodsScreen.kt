package hu.bme.aut.android.mealplanner.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.bme.aut.android.mealplanner.R
import hu.bme.aut.android.mealplanner.ui.components.AddNewFoodDialog
import hu.bme.aut.android.mealplanner.ui.components.ThemedBackground
import hu.bme.aut.android.mealplanner.viewmodel.FoodsViewModel
import hu.bme.aut.android.mealplanner.ui.components.MenuButton
import hu.bme.aut.android.mealplanner.ui.components.PageHeader
import hu.bme.aut.android.mealplanner.ui.theme.LobsterFont
import hu.bme.aut.android.mealplanner.ui.theme.PatrickHandFont

@Composable
fun FoodsScreen(navController: NavController) {
    val viewModel: FoodsViewModel = hiltViewModel()
    val foods by viewModel.foods.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }

    if (showAddDialog) {
        AddNewFoodDialog(
            onDismiss = { showAddDialog = false },
            onSave = { name, desc ->
                viewModel.addFood(name, desc)
                showAddDialog = false
            }
        )
    }

    ThemedBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            PageHeader(title = "Foods")

            Spacer(modifier = Modifier.weight(1f))

            // Paper-style table
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.paper),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    foods.forEach { food ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(0.5.dp, Color(0xFFc0b9a6))
                                .padding(horizontal = 4.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = food.name,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontFamily = PatrickHandFont,
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                ),
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = { viewModel.deleteFood(food) }) {
                                Image(
                                    painter = painterResource(id = R.drawable.trashcan),
                                    contentDescription = "Delete"
                                )
                            }
                        }
                    }

                    // Add new food row
                    TextButton(
                        onClick = { showAddDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .border(0.5.dp, Color(0xFFc0b9a6))
                            .background(MaterialTheme.colorScheme.onBackground)
                    ) {
                        Text(
                            text = "Add new food",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = LobsterFont,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Tapes
                Image(painter = painterResource(id = R.drawable.pinkflowertape), contentDescription = null, modifier = Modifier.align(Alignment.TopStart).rotate(-45f).offset((-8).dp, (-8).dp))
                Image(painter = painterResource(id = R.drawable.pinkflowertape), contentDescription = null, modifier = Modifier.align(Alignment.TopEnd).rotate(45f).offset((8).dp, (-8).dp))
                Image(painter = painterResource(id = R.drawable.pinkflowertape), contentDescription = null, modifier = Modifier.align(Alignment.BottomStart).rotate(45f).offset((-8).dp, (8).dp))
                Image(painter = painterResource(id = R.drawable.pinkflowertape), contentDescription = null, modifier = Modifier.align(Alignment.BottomEnd).rotate(-45f).offset((8).dp, (8).dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            MenuButton(label = "Menu", onClick = { navController.navigate("menu") })

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

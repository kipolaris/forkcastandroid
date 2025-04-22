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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import hu.bme.aut.android.mealplanner.domain.model.MealTime
import hu.bme.aut.android.mealplanner.ui.components.AddNewItemComponent
import hu.bme.aut.android.mealplanner.ui.components.EditNameDialog
import hu.bme.aut.android.mealplanner.ui.components.MenuButton
import hu.bme.aut.android.mealplanner.ui.components.PageHeader
import hu.bme.aut.android.mealplanner.ui.components.ThemedBackground
import hu.bme.aut.android.mealplanner.ui.theme.LobsterFont
import hu.bme.aut.android.mealplanner.viewmodel.MealTimesViewModel

@Composable
fun MealTimesScreen(navController: NavController) {
    val viewModel: MealTimesViewModel = hiltViewModel()
    val mealTimes by viewModel.mealTimes.collectAsState()

    var mealTimeToEdit by remember { mutableStateOf<MealTime?>(null) }

    mealTimeToEdit?.let { mealTime ->
        EditNameDialog(
            title = "Edit Meal Time",
            label = "Enter meal time name",
            initialName = mealTime.name,
            onConfirm = { newName ->
                viewModel.updateMealTime(mealTime.copy(name = newName))
                mealTimeToEdit = null
            },
            onDismiss = {
                mealTimeToEdit = null
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

            PageHeader(title = "Meal times")

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
                    mealTimes.sortedBy { it.order }.forEachIndexed { index, mealTime ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(0.5.dp, Color(0xFFc0b9a6))
                                .padding(horizontal = 4.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = {
                                    mealTimeToEdit = mealTime
                                }, modifier = Modifier.size(24.dp)) {
                                    Image(painter = painterResource(id = R.drawable.pencil), contentDescription = "Edit")
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                IconButton(onClick = {
                                    viewModel.deleteMealTime(mealTime)
                                }, modifier = Modifier.size(24.dp)) {
                                    Image(painter = painterResource(id = R.drawable.trashcan), contentDescription = "Delete")
                                }

                                Spacer(modifier = Modifier.width(4.dp))

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    if (index > 0) {
                                        IconButton(
                                            onClick = { viewModel.moveMealTimeUp(mealTime) },
                                            modifier = Modifier.size(20.dp)
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.arrowup),
                                                contentDescription = "Up",
                                                Modifier.size(20.dp)
                                            )
                                        }
                                    }

                                    if (index < mealTimes.size - 1) {
                                        IconButton(
                                            onClick = { viewModel.moveMealTimeDown(mealTime) },
                                            modifier = Modifier.size(20.dp)
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.arrowdown),
                                                contentDescription = "Down",
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }
                            }

                            Text(
                                text = mealTime.name,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontFamily = LobsterFont,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    textAlign = TextAlign.End
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // Add new meal time button
                    AddNewItemComponent(
                        label = "Meal Time",
                        addText = "Add new meal time",
                        onAddItem = { viewModel.addMealTime(it) }
                    )
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

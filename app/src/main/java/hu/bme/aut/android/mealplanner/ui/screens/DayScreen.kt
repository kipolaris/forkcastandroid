package hu.bme.aut.android.mealplanner.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import hu.bme.aut.android.mealplanner.ui.components.AddNewItemComponent
import hu.bme.aut.android.mealplanner.ui.components.MenuButton
import hu.bme.aut.android.mealplanner.ui.components.PageHeader
import hu.bme.aut.android.mealplanner.ui.components.SelectOrCreateFoodDialog
import hu.bme.aut.android.mealplanner.ui.components.ThemedBackground
import hu.bme.aut.android.mealplanner.ui.theme.LobsterFont
import hu.bme.aut.android.mealplanner.ui.theme.PatrickHandFont
import hu.bme.aut.android.mealplanner.viewmodel.DayViewModel

@Composable
fun DayScreen(navController: NavController, dayIndex: Int) {
    val viewModel: DayViewModel = hiltViewModel()

    LaunchedEffect(dayIndex) {
        viewModel.setDayIndex(dayIndex)
    }

    val day by viewModel.currentDay.collectAsState()
    val mealTimes by viewModel.mealTimes.collectAsState()
    val savedFoods by viewModel.savedFoods.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var selectedDayId by remember { mutableStateOf<Long?>(null) }
    var selectedMealTimeId by remember { mutableStateOf<Long?>(null) }

    if (showDialog) {
        SelectOrCreateFoodDialog(
            existingFoods = savedFoods,
            onDismiss = { showDialog = false },
            onFoodSelected = { selectedFood ->
                viewModel.assignFoodToMeal(selectedDayId!!, selectedMealTimeId!!, selectedFood)
                showDialog = false
            },
            onAddNewFood = { name, desc, onSaved ->
                viewModel.saveNewFood(name, desc) { savedFood ->
                    viewModel.assignFoodToMeal(selectedDayId!!, selectedMealTimeId!!, savedFood)
                    showDialog = false
                }
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

            PageHeader(
                title = day?.name ?: "",
                onPreviousClick = { viewModel.goToPreviousDay() },
                onNextClick = { viewModel.goToNextDay() }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Table with Meals and Tapes
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.paper),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .matchParentSize()
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(0.5.dp, MaterialTheme.colorScheme.onSecondary),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp)
                                .clickable {
                                    day?.id?.let { viewModel.resetDayMeals(it) }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Reset",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontFamily = LobsterFont,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .heightIn(min = 36.dp)
                                .background(MaterialTheme.colorScheme.onSecondary)
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Meals",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontFamily = LobsterFont,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                    }

                    mealTimes.sortedBy { it.order }.forEachIndexed { index, mealTime ->
                        val foodName = day?.meals?.firstOrNull { it.mealTime.id == mealTime.id }?.food?.name

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(0.5.dp, Color(0xFFc0b9a6)),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    if (index > 0) {
                                        IconButton(
                                            onClick = { viewModel.moveMealTimeUp(mealTime) },
                                            modifier = Modifier.size(20.dp)
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.arrowup),
                                                contentDescription = "Up",
                                                modifier = Modifier.size(20.dp)
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

                                Spacer(modifier = Modifier.width(6.dp))

                                Text(
                                    text = mealTime.name,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontFamily = LobsterFont,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                )
                            }


                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .heightIn(min = 46.dp)
                                    .background(Color(0xFFc0b9a6))
                            )

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(4.dp)
                                    .clickable {
                                        selectedMealTimeId = mealTime.id
                                        selectedDayId = day?.id
                                        showDialog = true
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = foodName ?: "",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontFamily = PatrickHandFont,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Light,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }

                        }
                    }

                    // Add new meal time
                    AddNewItemComponent(
                        label = "Meal Time",
                        addText = "Add new meal time",
                        onAddItem = { viewModel.addMealTime(it) }
                    )
                }

                // Tapes on table corners
                Image(
                    painter = painterResource(id = R.drawable.pinkflowertape),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .rotate(-45f)
                        .offset((-8).dp, (-8).dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.pinkflowertape),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .rotate(45f)
                        .offset((8).dp, (-8).dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.pinkflowertape),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .rotate(45f)
                        .offset((-8).dp, (8).dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.pinkflowertape),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .rotate(-45f)
                        .offset((8).dp, (8).dp)
                )
            }

            // Menu button
            Spacer(modifier = Modifier.weight(1f))

            MenuButton(
                label = "Menu",
                onClick = { navController.navigate("menu") }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


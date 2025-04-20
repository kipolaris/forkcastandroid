package hu.bme.aut.android.mealplanner.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.bme.aut.android.mealplanner.R
import hu.bme.aut.android.mealplanner.viewmodel.DayPageViewModel

@Composable
fun DayPageScreen(navController: NavController, dayIndex: Int) {
    val viewModel: DayPageViewModel = hiltViewModel()

    LaunchedEffect(dayIndex) {
        viewModel.setDayIndex(dayIndex)
    }

    val day by viewModel.currentDay.collectAsState()
    val currentIndex by viewModel.currentDayIndex.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.goToPreviousDay() }) {
                    Image(
                        painter = painterResource(id = R.drawable.arrowleft),
                        contentDescription = "Previous",
                        modifier = Modifier.size(48.dp)
                    )
                }
                Text(
                    text = day?.name ?: "",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color(0xFFFF78A8),
                        fontWeight = FontWeight.Bold
                    )
                )
                IconButton(onClick = { viewModel.goToNextDay() }) {
                    Image(
                        painter = painterResource(id = R.drawable.arrowright),
                        contentDescription = "Next",
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Table background layer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.paper),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(8.dp))
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    day?.meals?.forEach { meal ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = meal.mealTime.name,
                                color = Color(0xFFFF78A8),
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = meal.food?.name ?: "",
                                color = Color(0xFFFF78A8),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}
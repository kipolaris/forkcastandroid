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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.bme.aut.android.mealplanner.R
import hu.bme.aut.android.mealplanner.domain.model.Ingredient
import hu.bme.aut.android.mealplanner.ui.components.AddNewItemComponent
import hu.bme.aut.android.mealplanner.ui.components.EditNameDialog
import hu.bme.aut.android.mealplanner.ui.components.MenuButton
import hu.bme.aut.android.mealplanner.ui.components.PageHeader
import hu.bme.aut.android.mealplanner.ui.components.ThemedBackground
import hu.bme.aut.android.mealplanner.ui.theme.PatrickHandFont
import hu.bme.aut.android.mealplanner.viewmodel.IngredientsViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@Composable
fun IngredientsScreen(navController: NavController) {
    val viewModel: IngredientsViewModel = hiltViewModel()
    val ingredients by viewModel.ingredients.collectAsState()
    var ingredientToEdit by remember { mutableStateOf<Ingredient?>(null) }

    ingredientToEdit?.let { ingredient ->
        EditNameDialog(
            title = "Edit ingredient",
            label = "Enter ingredient name",
            initialName = ingredient.name,
            onConfirm = { newName ->
                viewModel.updateIngredient(ingredient.copy(name = newName))
                ingredientToEdit = null
            },
            onDismiss = { ingredientToEdit = null }
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

            PageHeader(title = "Ingredients")

            Spacer(modifier = Modifier.weight(1f))

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
                    LazyColumn(
                        modifier = Modifier
                            .padding(16.dp)
                            .heightIn(max = 440.dp)
                    ) {
                        items(ingredients.sortedBy { it.name.lowercase() }) { ingredient ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(0.5.dp, Color(0xFFc0b9a6))
                                    .padding(horizontal = 4.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = ingredient.name,
                                    fontFamily = PatrickHandFont,
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.weight(1f)
                                )
                                Row {
                                    IconButton(onClick = { ingredientToEdit = ingredient }) {
                                        Image(
                                            painter = painterResource(id = R.drawable.pencil),
                                            contentDescription = "Edit"
                                        )
                                    }
                                    IconButton(onClick = { viewModel.deleteIngredient(ingredient) }) {
                                        Image(
                                            painter = painterResource(id = R.drawable.trashcan),
                                            contentDescription = "Delete"
                                        )
                                    }
                                }
                            }
                        }
                    }

                    AddNewItemComponent(
                        label = "Ingredient",
                        addText = "Add new ingredient",
                        onAddItem = { viewModel.addIngredient(it) }
                    )
                }

                // Tapes
                listOf(
                    Modifier.align(Alignment.TopStart).rotate(-45f).offset((-8).dp, (-8).dp),
                    Modifier.align(Alignment.TopEnd).rotate(45f).offset((8).dp, (-8).dp),
                    Modifier.align(Alignment.BottomStart).rotate(45f).offset((-8).dp, (8).dp),
                    Modifier.align(Alignment.BottomEnd).rotate(-45f).offset((8).dp, (8).dp)
                ).forEach {
                    Image(
                        painter = painterResource(id = R.drawable.pinkflowertape),
                        contentDescription = null,
                        modifier = it
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            MenuButton(label = "Menu", onClick = { navController.navigate("menu") })

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

package hu.bme.aut.android.mealplanner.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.bme.aut.android.mealplanner.R
import hu.bme.aut.android.mealplanner.domain.model.Ingredient
import hu.bme.aut.android.mealplanner.ui.components.EditNameDialog
import hu.bme.aut.android.mealplanner.ui.components.MenuButton
import hu.bme.aut.android.mealplanner.ui.components.PageHeader
import hu.bme.aut.android.mealplanner.ui.components.ThemedBackground
import hu.bme.aut.android.mealplanner.ui.theme.LobsterFont
import hu.bme.aut.android.mealplanner.ui.theme.PatrickHandFont
import hu.bme.aut.android.mealplanner.viewmodel.FoodViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import hu.bme.aut.android.mealplanner.ui.components.SelectOrCreateItemDialog


@Composable
fun FoodScreen(
    navController: NavController
) {
    val viewModel: FoodViewModel = hiltViewModel()
    val food by viewModel.food.collectAsState()
    val ingredients by viewModel.ingredients.collectAsState()

    if (food == null) return

    var editingDescription by remember { mutableStateOf(false) }
    var editingIngredient by remember { mutableStateOf<Ingredient?>(null) }
    val editingQuantity by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }
    var showQuantityDialog by remember { mutableStateOf(false) }

    ThemedBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            PageHeader(title = food!!.name)

            Spacer(modifier = Modifier.height(32.dp))

            // Description
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(vertical = 12.dp)
                    .background(Color.White)
                    .clickable { editingDescription = true }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.paper),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )
                Box(modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = food!!.description.toString(),
                        fontFamily = PatrickHandFont,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }

                listOf(
                    Modifier.align(Alignment.TopStart).rotate(-45f).offset((-8).dp, (-8).dp),
                    Modifier.align(Alignment.TopEnd).rotate(45f).offset((8).dp, (-8).dp),
                    Modifier.align(Alignment.BottomStart).rotate(45f).offset((-8).dp, (8).dp),
                    Modifier.align(Alignment.BottomEnd).rotate(-45f).offset((8).dp, (8).dp)
                ).forEach {
                    Image(painter = painterResource(id = R.drawable.pinkflowertape), contentDescription = null, modifier = it)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Ingredients
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 280.dp)
                    .background(Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.paper),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    LazyColumn(
                        modifier = Modifier
                            .heightIn(max = 200.dp)
                    ) {
                        items(food!!.ingredients.orEmpty().sortedBy { it.name.lowercase() }) { ingredient ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(0.5.dp, Color(0xFFc0b9a6))
                                    .padding(8.dp)
                                    .clickable {
                                        editingIngredient = ingredient
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    ingredient.name,
                                    fontFamily = PatrickHandFont,
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f)

                                )
                                Text(
                                    ingredient.quantity.orEmpty(),
                                    fontFamily = PatrickHandFont,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Light,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                                IconButton(onClick = {
                                    viewModel.removeIngredient(ingredient.id)
                                }) {
                                    Image(painter = painterResource(id = R.drawable.trashcan), contentDescription = "Delete")
                                }
                            }
                        }
                    }

                    TextButton(
                        onClick = { showAddDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .border(0.5.dp, Color(0xFFc0b9a6))
                            .background(MaterialTheme.colorScheme.onBackground)
                    ) {
                        Text(
                            text = "Add new ingredient",
                            fontFamily = LobsterFont,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                listOf(
                    Modifier.align(Alignment.TopStart).rotate(-45f).offset((-8).dp, (-8).dp),
                    Modifier.align(Alignment.TopEnd).rotate(45f).offset((8).dp, (-8).dp),
                    Modifier.align(Alignment.BottomStart).rotate(45f).offset((-8).dp, (8).dp),
                    Modifier.align(Alignment.BottomEnd).rotate(-45f).offset((8).dp, (8).dp)
                ).forEach {
                    Image(painter = painterResource(id = R.drawable.pinkflowertape), contentDescription = null, modifier = it)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MenuButton(label = "Foods", onClick = { navController.navigate("foods") }, modifier = Modifier.weight(1f))

                MenuButton(label = "Menu", onClick = { navController.navigate("menu") }, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Quantity edit dialog
    editingIngredient?.let { ingredient ->
        EditNameDialog(
            title = "Edit quantity",
            label = "Enter quantity",
            initialName = editingQuantity,
            onConfirm = { newQty ->
                viewModel.updateIngredientQuantity(ingredient.id, newQty)
                editingIngredient = null
            },
            onDismiss = { editingIngredient = null }
        )
    }

    if (showAddDialog) {
        SelectOrCreateItemDialog(
            label = "Ingredient",
            items = ingredients,
            getItemName = { it.name },
            onDismiss = { showAddDialog = false },
            onItemSelected = { selectedIngredient ->
                editingIngredient = selectedIngredient
                showQuantityDialog = true
                showAddDialog = false
            },
            onAddNewItem = { name, quantity, _ ->
                viewModel.addNewIngredientAndAssign(name, quantity)
                showAddDialog = false
            }
        )
    }

    if (editingDescription) {
        EditNameDialog(
            title = "Edit description",
            label = "Enter description",
            initialName = food!!.description.orEmpty(),
            onConfirm = { newDesc ->
                viewModel.updateFoodDescription(newDesc)
                editingDescription = false
            },
            onDismiss = { editingDescription = false }
        )
    }

    if (showQuantityDialog && editingIngredient != null) {
        EditNameDialog(
            title = "Enter quantity",
            label = "Quantity",
            initialName = "",
            onConfirm = { qty ->
                viewModel.addIngredient(editingIngredient!!, qty)
                showQuantityDialog = false
                editingIngredient = null
            },
            onDismiss = {
                showQuantityDialog = false
                editingIngredient = null
            }
        )
    }

}

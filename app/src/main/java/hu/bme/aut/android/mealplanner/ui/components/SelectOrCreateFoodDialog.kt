package hu.bme.aut.android.mealplanner.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.android.mealplanner.domain.model.Food
import hu.bme.aut.android.mealplanner.ui.theme.LobsterFont
import hu.bme.aut.android.mealplanner.ui.theme.PatrickHandFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectOrCreateFoodDialog(
    existingFoods: List<Food>,
    onDismiss: () -> Unit,
    onFoodSelected: (Food) -> Unit,
    onAddNewFood: (String, String, (Food) -> Unit) -> Unit
) {
    var isAddingNew by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var selectedFood: Food? by remember { mutableStateOf(null) }

    var newName by remember { mutableStateOf("") }
    var newDesc by remember { mutableStateOf("") }

    if (isAddingNew) {
        AlertDialog(
            onDismissRequest = { isAddingNew = false },
            title = {
                Text("Add new food", fontFamily = LobsterFont, fontSize = 24.sp)
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = newName,
                        onValueChange = { newName = it },
                        label = {
                            Text(
                                "Food name",
                                fontFamily = PatrickHandFont,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSecondary
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newDesc,
                        onValueChange = { newDesc = it },
                        label = {
                            Text("Description",
                                fontFamily = PatrickHandFont,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSecondary
                        )
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onAddNewFood(newName.trim(), newDesc.trim()) { savedFood ->
                            onFoodSelected(savedFood)
                            isAddingNew = false
                        }
                    },
                    Modifier.background(MaterialTheme.colorScheme.onBackground)
                ) {
                    Text(
                        text = "Save",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = LobsterFont,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { isAddingNew = false },
                    Modifier.background(MaterialTheme.colorScheme.onBackground)
                ) {
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = LobsterFont,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        )
    } else {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text("Select a food", fontFamily = LobsterFont, fontSize = 24.sp)
            },
            text = {
                Column {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },

                    ) {
                        OutlinedTextField(
                            value = selectedFood?.name ?: "Select a food",
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSecondary
                            )
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            existingFoods.sortedBy { it.name }.forEach { food ->
                                DropdownMenuItem(
                                    text = { Text(food.name, fontFamily = PatrickHandFont) },
                                    onClick = {
                                        selectedFood = food
                                        expanded = false
                                        onFoodSelected(food)
                                    },
                                    modifier = Modifier.border(0.5.dp, MaterialTheme.colorScheme.onSecondary)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(
                        onClick = { isAddingNew = true },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .background(MaterialTheme.colorScheme.onBackground)
                            .fillMaxWidth()
                    ) {
                        Text(
                            "or add a new one",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = LobsterFont,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)
                ) {
                    Text(
                        "Cancel",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = LobsterFont,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        )
    }
}


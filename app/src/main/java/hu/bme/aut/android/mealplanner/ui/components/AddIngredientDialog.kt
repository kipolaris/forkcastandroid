package hu.bme.aut.android.mealplanner.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.mealplanner.domain.model.Ingredient
import hu.bme.aut.android.mealplanner.domain.model.UnitOfMeasure

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIngredientDialog(
    onDismiss: () -> Unit,
    onSave: (Ingredient?, String, Double, UnitOfMeasure) -> Unit,
    savedIngredients: List<Ingredient>,
    units: List<UnitOfMeasure>
) {
    var selectedIngredient by remember { mutableStateOf<Ingredient?>(null) }
    var selectedUnit by remember { mutableStateOf<UnitOfMeasure?>(null) }
    var newIngredient by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    var isDropdownOpen by remember { mutableStateOf(false) }
    var isUnitDropdownOpen by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select or Add Ingredient") },
        text = {
            Column {
                ExposedDropdownMenuBox(
                    expanded = isDropdownOpen,
                    onExpandedChange = { isDropdownOpen = !isDropdownOpen }
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = selectedIngredient?.name ?: "Select ingredient",
                        onValueChange = {},
                        modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, true).fillMaxWidth(),
                        label = { Text("Ingredient") }
                    )
                    ExposedDropdownMenu(
                        expanded = isDropdownOpen,
                        onDismissRequest = { isDropdownOpen = false }
                    ) {
                        savedIngredients.sortedBy { it.name }.forEach { ingredient ->
                            DropdownMenuItem(
                                text = { Text(ingredient.name) },
                                onClick = {
                                    selectedIngredient = ingredient
                                    isDropdownOpen = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = newIngredient,
                    onValueChange = { newIngredient = it },
                    label = { Text("Or add new ingredient") },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )

                // AMOUNT FIELD
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )

                // UNIT DROPDOWN
                ExposedDropdownMenuBox(
                    expanded = isUnitDropdownOpen,
                    onExpandedChange = { isUnitDropdownOpen = !isUnitDropdownOpen }
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = selectedUnit?.name ?: "Select unit",
                        onValueChange = {},
                        modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, true).fillMaxWidth(),
                        label = { Text("Unit") }
                    )
                    ExposedDropdownMenu(
                        expanded = isUnitDropdownOpen,
                        onDismissRequest = { isUnitDropdownOpen = false }
                    ) {
                        units.forEach { unit ->
                            DropdownMenuItem(
                                text = { Text(unit.name) },
                                onClick = {
                                    selectedUnit = unit
                                    isUnitDropdownOpen = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val amt = amount.toDoubleOrNull() ?: return@TextButton
                    selectedUnit?.let { unit ->
                        onSave(selectedIngredient, newIngredient.trim(), amt, unit)
                    }
                },
                enabled = (selectedIngredient != null || newIngredient.isNotBlank()) && amount.isNotBlank() && selectedUnit != null
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

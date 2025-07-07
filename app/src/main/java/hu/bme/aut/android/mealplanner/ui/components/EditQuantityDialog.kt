package hu.bme.aut.android.mealplanner.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import hu.bme.aut.android.mealplanner.domain.model.UnitOfMeasure

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditQuantityDialog(
    onDismiss: () -> Unit,
    onConfirm: (Double, UnitOfMeasure) -> Unit,
    initialAmount: Double,
    initialUnit: UnitOfMeasure,
    units: List<UnitOfMeasure>
) {
    var amount by remember { mutableStateOf(initialAmount.toString()) }
    var selectedUnit by remember { mutableStateOf(initialUnit) }
    var isDropdownOpen by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit quantity") },
        text = {
            Column {
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(
                    expanded = isDropdownOpen,
                    onExpandedChange = { isDropdownOpen = !isDropdownOpen }
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = selectedUnit.name,
                        onValueChange = {},
                        modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, true).fillMaxWidth(),
                        label = { Text("Unit") }
                    )

                    ExposedDropdownMenu(
                        expanded = isDropdownOpen,
                        onDismissRequest = { isDropdownOpen = false }
                    ) {
                        units.forEach { unit ->
                            DropdownMenuItem(
                                text = { Text(unit.name) },
                                onClick = {
                                    selectedUnit = unit
                                    isDropdownOpen = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                amount.toDoubleOrNull()?.let {
                    onConfirm(it, selectedUnit)
                }
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
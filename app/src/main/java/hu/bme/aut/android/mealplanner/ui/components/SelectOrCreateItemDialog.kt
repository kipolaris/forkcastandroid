package hu.bme.aut.android.mealplanner.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
fun <T> SelectOrCreateItemDialog(
    label: String,
    items: List<T>,
    getItemName: (T) -> String,
    onDismiss: () -> Unit,
    onItemSelected: (T) -> Unit,
    onAddNewItem: (String, String, (T) -> Unit) -> Unit
) {
    var isAddingNew by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var selectedItem: T? by remember { mutableStateOf(null) }

    if (isAddingNew) {
        AddNewItemDialog(
            label = label,
            nameLabel = "$label name",
            descLabel = if (label.lowercase() == "ingredient") "Quantity" else "Description",
            onDismiss = { isAddingNew = false },
            onSave = { name, desc ->
                onAddNewItem(name, desc) { newItem ->
                    selectedItem = newItem
                    onItemSelected(newItem)
                    isAddingNew = false
                }
            }
        )
        return
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Select a $label", fontFamily = LobsterFont, fontSize = 24.sp)
        },
        text = {
            Column {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                ) {
                    OutlinedTextField(
                        value = selectedItem?.let(getItemName) ?: "Select a $label",
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
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.heightIn(max = 300.dp)
                    ) {
                        items.sortedBy(getItemName).forEach { item ->
                            DropdownMenuItem(
                                text = { Text(getItemName(item), fontFamily = PatrickHandFont) },
                                onClick = {
                                    selectedItem = item
                                    expanded = false
                                    onItemSelected(item)
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
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }
    )
}




package hu.bme.aut.android.mealplanner.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.android.mealplanner.ui.theme.LobsterFont

@Composable
fun AddNewItemComponent(
    label: String,
    addText: String,
    onAddItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    var newItemName by remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = "New $label",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = LobsterFont,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                )
            },
            text = {
                OutlinedTextField(
                    value = newItemName,
                    onValueChange = { newItemName = it },
                    label = {
                        Text(
                            text = "Enter $label name",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = LobsterFont,
                                fontWeight = FontWeight.Light,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                    }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onAddItem(newItemName.trim())
                        newItemName = ""
                        showDialog = false
                    },
                    modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)
                ) {
                    Text(
                        "Add",
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
                    onClick = {
                        newItemName = ""
                        showDialog = false
                    },
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

    TextButton(
        onClick = { showDialog = true },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .border(0.5.dp, Color(0xFFc0b9a6))
            .background(MaterialTheme.colorScheme.onBackground)
    ) {
        Text(
            text = addText,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .fillMaxWidth(),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = LobsterFont,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
        )
    }
}

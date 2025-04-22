package hu.bme.aut.android.mealplanner.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.android.mealplanner.ui.theme.LobsterFont

@Composable
fun AddNewMealTimeComponent(
    onAddMealTime: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    var newMealTimeName by remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = "New Meal Time",
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
                    value = newMealTimeName,
                    onValueChange = { newMealTimeName = it },
                    label = {
                        Text(
                            text = "Enter name",
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
                        onAddMealTime(newMealTimeName.trim())
                        newMealTimeName = ""
                        showDialog = false
                    },
                    Modifier.background(MaterialTheme.colorScheme.onBackground)
                ) {
                    Text(
                        text = "Add",
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
                        newMealTimeName = ""
                        showDialog = false
                    },
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
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(0.5.dp, Color(0xFFc0b9a6))
            .clickable { showDialog = true }
    ) {
        Text(
            text = "Add new meal time",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(4.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = LobsterFont,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        )
    }
}

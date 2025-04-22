package hu.bme.aut.android.mealplanner.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import hu.bme.aut.android.mealplanner.ui.theme.LobsterFont

@Composable
fun EditMealTimeDialog(
    initialName: String,
    onEditMealTime: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var newMealTimeName by remember { mutableStateOf(initialName) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Edit Meal Time",
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
                    onEditMealTime(newMealTimeName.trim())
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
                onClick = onDismiss,
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

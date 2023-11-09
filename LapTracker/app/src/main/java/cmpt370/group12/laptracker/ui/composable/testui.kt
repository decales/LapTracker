package cmpt370.group12.laptracker.ui.composable

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmpt370.group12.laptracker.viewModel.ApplicationViewModel


@Composable

fun me(viewModel: ApplicationViewModel) {
    var showDialog by remember { mutableStateOf(false) }

    Button(
        //elevation = ButtonDefaults.buttonElevation(
        //    defaultElevation = 10.dp ),
        onClick = {
            //showDialog = showDialog.not()
            viewModel.SetLocationFollow(viewModel.mapstate.value.currentLocationFollow.not())


        }) {
        Text("Delete")
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Are you sure you want to delete this?") },
            text = { Text("This action cannot be undone") },
            confirmButton = {
                TextButton(onClick = { /* TODO */ }) {
                    Text("Delete it".uppercase())
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel".uppercase())
                }
            },
        )
    }
}
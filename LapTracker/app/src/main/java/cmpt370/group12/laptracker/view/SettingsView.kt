package cmpt370.group12.laptracker.view.main

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cmpt370.group12.laptracker.old.Feature1
import cmpt370.group12.laptracker.old.Feature10
import cmpt370.group12.laptracker.old.Feature11
import cmpt370.group12.laptracker.old.Feature12
import cmpt370.group12.laptracker.old.Feature13
import cmpt370.group12.laptracker.old.Feature14
import cmpt370.group12.laptracker.old.Feature2
import cmpt370.group12.laptracker.old.Feature3
import cmpt370.group12.laptracker.old.Feature4
import cmpt370.group12.laptracker.old.Feature5
import cmpt370.group12.laptracker.old.Feature6
import cmpt370.group12.laptracker.old.Feature7
import cmpt370.group12.laptracker.old.Feature8
import cmpt370.group12.laptracker.old.Feature9
import cmpt370.group12.laptracker.viewmodel.main.SettingsViewModel

class SettingsView(viewModel: SettingsViewModel) {


    /* View:
        - Represent the entire settings view
        - Made up of class defined composable functions
        - All data is stored retrieved from class view model */
    @Composable
    fun View() {

        // TODO build view from class defined composable functions.
        // TODO initialize necessary view data in viewmodel/main/SettingsViewModel.kt. Data is accessed through constructor var 'viewModel'

        // TODO temporary to launch test feature activities, delete later
        val context = LocalContext.current
        val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
        Column {
            Header()
            for (i in 1..14) {
                val activity = when(i) {
                    1 -> Feature1::class.java
                    2 -> Feature2::class.java
                    3 -> Feature3::class.java
                    4 -> Feature4::class.java
                    5 -> Feature5::class.java
                    6 -> Feature6::class.java
                    7 -> Feature7::class.java
                    8 -> Feature8::class.java
                    9 -> Feature9::class.java
                    10 -> Feature10::class.java
                    11 -> Feature11::class.java
                    12 -> Feature12::class.java
                    13 -> Feature13::class.java
                    14 -> Feature14::class.java
                    else -> Feature1::class.java
                }
                Button(onClick = { launcher.launch(Intent(context, activity))}) {
                    Text(text = "$i")
                }
            }
        }
    }

    @Composable
    fun Header() {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp)
        )
        {
            Text(
                text = "Settings",
                fontSize = 30.sp
            )
        }
    }
}
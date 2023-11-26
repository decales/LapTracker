package cmpt370.group12.laptracker.old
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import cmpt370.group12.laptracker.presentation.AppState
import cmpt370.group12.laptracker.view.theme.LapTrackerTheme

class Feature13 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LapTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting14("Feature 13")
                    RunSelector()
                }
            }
        }
    }
}

@Composable
fun RunSelector (){
    var dropOpen by remember { mutableStateOf(false) }
    val selectedItems = remember { mutableStateListOf<String>() }
    val runs = AppState().runs
    val names: List<String> = runs.map { it.id.toString() }
    //val options = { mutableStateListOf<String>(names) }
    val options = names.toMutableList()
    val selectedRunsViewModel = viewModel<SelectedRunsViewModel>()
    Column(
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End
    ){
        Text(
                text = "Run analysis",
                style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .wrapContentHeight(Alignment.CenterVertically)
        )
        Button(
                onClick = { dropOpen = true },
                modifier = Modifier.padding(16.dp)
        ) {
            Text("Select")
        }
        if (dropOpen){
            AlertDialog(
                    onDismissRequest = { dropOpen = false },
                    //the title of it when it opens
                    title = { Text(text = "Items") },
                    text = {
                        Column {
                            options.forEach { option ->
                                val isChecked = selectedItems.contains(option)
                                Row(
                                        //center them within the dropdown menu
                                        verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Checkbox(
                                            checked = isChecked,
                                            onCheckedChange = { isChecked ->
                                                if (isChecked) {
                                                    selectedItems.add(option)
                                                    //toggle the view on SELECT
                                                    selectedRunsViewModel.toggleItem(option)
                                                } else {
                                                    selectedItems.remove(option)
                                                    //toggle the view on DESELECT
                                                    selectedRunsViewModel.toggleItem(option)
                                                }
                                            }
                                    )
                                    Spacer ( modifier = Modifier.width(8.dp))
                                    Text ( text = option)
                                }
                            }
                        }
                    },
                    confirmButton = {
                        //leave this blank the user can just tapout
                    }
            )
        }
        //Text ("The Dropdown composable for lap selection")
    }
}
//Todo
//create an encode or some way to get the names of all the runs available
class SelectedRunsViewModel: ViewModel() {
    val selectedItems = mutableStateOf(emptyList<String>())
    fun toggleItem(item: String){
        val selected = selectedItems.value.toMutableList()
        if (selected.contains(item)){
            selected.remove(item)
        } else {
            selected.add(item)
        }
        selectedItems.value = selected
    }
}

@Composable
fun Greeting14(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview14() {
    LapTrackerTheme {
        Greeting14("Android")
    }
}
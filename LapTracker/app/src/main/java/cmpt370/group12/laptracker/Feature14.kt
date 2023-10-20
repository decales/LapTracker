package cmpt370.group12.laptracker
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import cmpt370.group12.laptracker.ui.content.BottomNavigation
import cmpt370.group12.laptracker.ui.content.NavBarContent
import cmpt370.group12.laptracker.ui.content.NavigationView
import cmpt370.group12.laptracker.ui.theme.LapTrackerTheme

class Feature14 : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LapTrackerTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Scaffold (
                        bottomBar = {
                            BottomNavigation(
                                navController = navController,
                                onClick = { navController.navigate(it.route)},
                                items = listOf(
                                    NavBarContent("Configure", R.drawable.configtrack),
                                    NavBarContent("Start", R.drawable.newtrack),
                                    NavBarContent("History", R.drawable.prevtrack)
                                ))
                        }
                    ) {
                        NavigationView(this, navController = navController)
                    }
                }
            }
        }
    }
}
package cmpt370.group12.laptracker.view.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cmpt370.group12.laptracker.R
import cmpt370.group12.laptracker.view.main.profile.ProfileView
import cmpt370.group12.laptracker.view.main.settings.SettingsView
import cmpt370.group12.laptracker.view.main.start.StartView
import cmpt370.group12.laptracker.view.main.tracks.TracksView
import cmpt370.group12.laptracker.view.theme.LapTrackerTheme
import cmpt370.group12.laptracker.viewmodel.main.ProfileViewModel
import cmpt370.group12.laptracker.viewmodel.main.SettingsViewModel
import cmpt370.group12.laptracker.viewmodel.main.StartViewModel
import cmpt370.group12.laptracker.viewmodel.main.TracksViewModel

class MainActivity : ComponentActivity() {

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
        LapTrackerTheme {
            Surface (
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val controller = rememberNavController() // Navigation controller
                Scaffold (
                    bottomBar = {
                        BottomNavigationBar(controller)
                    }
                ) {
                    NavigationView(controller)
                }
            }
            }
        }
    }


    /* NavigationView:
        - Represents the current view (the entire area above the navigation bar)
        - Current view is set by the navigation controller
        - Navigation controller sets view based on the 'route' passed from the navigation bar */
    @Composable
    fun NavigationView(navController: NavHostController) {

        NavHost(navController = navController, startDestination = "Start") {

            // Initialize view models to pass to associated views
            // TODO fix view models to preserve states on MainActivity re-compose (i.e. on screen rotation)
            val startViewModel = StartViewModel()
            val tracksViewModel = TracksViewModel()
            val profileViewModel = ProfileViewModel()
            val settingsViewModel = SettingsViewModel()

            // Handle routes
            composable("Start") {
                StartView(startViewModel)
            }
            composable("Tracks") {
                TracksView(tracksViewModel)
            }
            composable("Profile") {
                ProfileView(profileViewModel)
            }
            composable("Settings") {
                SettingsView(settingsViewModel)
            }
        }
    }


    /* BottomNavigationBar:
        - Composable to set the current view of the navigation controller
        - When an item is selected, its 'route' is passed to the navigation controller
        - Bar persists throughout the MainActivity */
    @Composable
    fun BottomNavigationBar(controller: NavController) {
        NavigationBar (
            content = {
                listOf(
                    // Route (first), icon (second)
                    Pair("Start", R.drawable.newtrack),
                    Pair("Tracks", R.drawable.newtrack),
                    Pair("Profile", R.drawable.prevtrack),
                    Pair("Settings", R.drawable.configtrack)
                ).forEach { item ->
                    NavigationBarItem (
                        selected = item.first == controller.currentBackStackEntryAsState().value?.destination?.route,
                        onClick = { controller.navigate(item.first) },
                        icon = {
                            Column (
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painter = painterResource(id = item.second),
                                    contentDescription = item.first,
                                    modifier = Modifier.size(40.dp)
                                )
                                Text (
                                    text = item.first,
                                    modifier = Modifier.scale(0.6F, 0.6F)
                                )
                            }
                        }
                    )
                }
            }
        )
    }
}








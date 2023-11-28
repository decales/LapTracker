package cmpt370.group12.laptracker.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import cmpt370.group12.laptracker.model.LocationClient
import cmpt370.group12.laptracker.view.main.SettingsView
import cmpt370.group12.laptracker.view.theme.LapTrackerTheme
import cmpt370.group12.laptracker.viewmodel.ProfileViewModel
import cmpt370.group12.laptracker.viewmodel.SettingsViewModel
import cmpt370.group12.laptracker.viewmodel.StartViewModel
import cmpt370.group12.laptracker.viewmodel.TracksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
        LapTrackerTheme {
            Surface (
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.fillMaxSize()
            ) {
                val controller = rememberNavController() // Navigation controller
                Scaffold (
                    bottomBar = { BottomNavigationBar(controller) },
                ) { navBarPadding ->
                    NavigationView(controller, navBarPadding)
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
    fun NavigationView(
        navController: NavHostController,
        navBarPadding: PaddingValues,
        startViewModel: StartViewModel = viewModel(),
        tracksViewModel: TracksViewModel = viewModel(),
        profileViewModel: ProfileViewModel = viewModel(),
        settingsViewModel: SettingsViewModel = viewModel()
    ) {
        NavHost(
            navController = navController,
            startDestination = "Start",
            modifier = Modifier.padding(navBarPadding)
        ) {

            // Late init locationClient in startViewmodel
            startViewModel.locationClient = LocationClient(this@MainActivity)

            // Handle routes
            composable("Start") {
                StartView(startViewModel).View()
            }
            composable("Tracks") {
                TracksView(tracksViewModel).View()
            }
            composable("Profile") {
                ProfileView(profileViewModel).View()
            }
            composable("Settings") {
                SettingsView(settingsViewModel).View()
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








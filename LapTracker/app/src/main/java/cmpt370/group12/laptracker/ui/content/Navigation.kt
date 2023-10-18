package cmpt370.group12.laptracker.ui.content

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import cmpt370.group12.laptracker.MainActivity


data class NavBarContent(
    val route: String,
    @DrawableRes val icon: Int,
)


@Composable
fun NavigationView(activity: Activity, navController: NavHostController) {
    NavHost(navController = navController, startDestination = "Start") {

        composable("Configure") {
            ConfigureView()
        }
        composable("Start") {
            StartView(activity)
        }
        composable("History") {
            HistoryView()
        }
    }
}


@Composable
fun BottomNavigation(items: List<NavBarContent>, navController: NavController, onClick: (NavBarContent) -> Unit) {
    NavigationBar (
        content = {
            items.forEach { item ->
                NavigationBarItem (
                    selected = item.route == navController.currentBackStackEntryAsState().value?.destination?.route,
                    onClick = { onClick(item) },
                    icon = {
                        Column (
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.route,
                                modifier = Modifier.size(40.dp)
                            )
                            Text (
                                text = item.route,
                                modifier = Modifier.scale(0.6F, 0.6F)
                            )
                        }
                    }
                )
            }
        }
    )
}



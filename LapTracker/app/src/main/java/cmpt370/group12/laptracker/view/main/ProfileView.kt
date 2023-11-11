package cmpt370.group12.laptracker.view.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cmpt370.group12.laptracker.viewmodel.main.ProfileViewModel

class ProfileView(
    private val viewModel: ProfileViewModel
) {
    /* View:
        - Represent the entire profile view
        - Made up of class defined composable functions
        - All data is stored retrieved from class view model */
    @Composable
    fun View() {
        ProfileTabBar()
    }

    @Composable
    fun ProfileTabBar() {
        Box(modifier = Modifier.fillMaxSize()) {
            TabRow(
                selectedTabIndex = viewModel.currentTab
            ) {
                viewModel.profileTabs.forEach { item ->
                    Tab(
                        selected = false,
                        onClick = { viewModel.setTab()}
                    )
                    {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = item.text)
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.text,
                                modifier = Modifier
                                    .size(50.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
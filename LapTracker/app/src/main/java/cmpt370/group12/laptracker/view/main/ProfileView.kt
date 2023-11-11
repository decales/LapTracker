package cmpt370.group12.laptracker.view.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun View() {
        Column {
            val pagerState = rememberPagerState { viewModel.profileTabs.size }
            LaunchedEffect(viewModel.currentPage) { pagerState.scrollToPage(viewModel.currentPage) }
            ProfileTabBar()
            HorizontalPager(state = pagerState) {
                when (it) {
                    0 -> StatisticsView()
                    1 -> AchievementsView()
                    else -> Text(text = "?")
                }
            }
        }
    }

    @Composable
    fun StatisticsView() {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Statistics")
        }
    }


    @Composable
    fun AchievementsView() {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Achievements")
        }
    }


    @Composable
    fun ProfileTabBar() {
        TabRow(
            selectedTabIndex = viewModel.currentPage
        ) {
            viewModel.profileTabs.forEachIndexed { index, item ->
                Tab(
                    selected = false,
                    onClick = { viewModel.setPage(index) }
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
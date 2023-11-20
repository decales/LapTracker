package cmpt370.group12.laptracker.view.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cmpt370.group12.laptracker.model.domain.model.Achievement
import cmpt370.group12.laptracker.viewmodel.ProfileViewModel

class ProfileView(
    private val viewModel: ProfileViewModel
) {
    /* View:
        - Represent the entire profile view
        - Made up of class defined composable functions
        - Contains two sub-views for statistics and achievements sections
        - All data is stored retrieved from class view model */
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun View() {
        Column {
            val pagerState = rememberPagerState { viewModel.profileTabs.size }
            LaunchedEffect(viewModel.currentPage) { pagerState.scrollToPage(viewModel.currentPage) }
            // TODO fix pagerState and viewModel.currentPage so tabs are in sync with page selection using both swipe and tab selection
            Header()
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
    fun Header() {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp)
        )
        {
            Text(
                text = "My Profile",
                fontSize = 30.sp
            )
        }
    }


    @Composable
    fun ProfileTabBar() {
        TabRow(
            selectedTabIndex = viewModel.currentPage,
            modifier = Modifier.padding(top = 5.dp)
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


    /* StatisticsView:
        - Represent the view of the statistics section
        - Sub-view of main profile view
        - All data is stored retrieved from class view model */
    @Composable
    fun StatisticsView() {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Statistics")
        }
    }


    /* AchievementsView:
        - Represent the view of the achievements section
        - Sub-view of main profile view
        - All data is stored retrieved from class view model */
    @Composable
    fun AchievementsView() {
        Column {
            CompletionText()
            AchievementsGrid()
        }
        if (viewModel.achievementDetailsVisible) {
            AchievementDetails(viewModel.currentAchievement)
        }
    }


    @Composable
    fun CompletionText() {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "${viewModel.backend.appstate.value.achievements.count { it.achieved }}/${viewModel.backend.appstate.value.achievements.size} Unlocked",
                    fontSize = 14.sp
                )
                Text(text = "${(viewModel.backend.appstate.value.achievements.count { it.achieved }.toDouble() / viewModel.backend.appstate.value.achievements.size.toDouble() * 100).toInt()}% Completion",
                    fontSize = 14.sp
                )
            }
        }
    }


    @Composable
    fun AchievementsGrid() {
        Card (
            colors = CardDefaults.cardColors(Color(0xff1c212d)),
            modifier = Modifier.padding(20.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(top = 20.dp),
                content = {
                    viewModel.backend.appstate.value.achievements.forEach { achievement ->
                        item {
                            Column (
                                horizontalAlignment = CenterHorizontally,
                                modifier = Modifier.padding(bottom = 20.dp, start = 20.dp, end = 20.dp)
                            ) {
                                Card (
                                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                                    modifier = Modifier
                                        .padding(bottom = 5.dp)
                                        .clickable { viewModel.toggleAchievementDetails(achievement) }
                                ) {
                                    Icon(
                                        painter = painterResource(id = achievement.iconID),
                                        contentDescription = achievement.name,
                                        tint = if (!achievement.achieved) Color.Gray else Color.Unspecified // Grey-out locked achievements
                                    )
                                }
                                Text(
                                    text = achievement.name,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            )
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AchievementDetails(achievement: Achievement) {
        AlertDialog(
            onDismissRequest = { viewModel.toggleAchievementDetails(achievement) }
        ) {
            Card (modifier = Modifier
                .padding(0.dp)
            ) {
                Column(
                    horizontalAlignment = CenterHorizontally,
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 20.dp)

                        .align(CenterHorizontally)
                ) {
                    Text(
                        text = achievement.name,
                        fontSize = 22.sp
                    )
                    Icon(
                        painter = painterResource(id = achievement.iconID),
                        contentDescription = achievement.name,
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 5.dp)
                            .border(1.dp, Color.White, RoundedCornerShape(15.dp))
                    )
                    Text(text = achievement.description)
                    Text(
                        text = achievement.timestamp.toString(),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
            }
        }
    }
}
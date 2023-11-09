package cmpt370.group12.laptracker.ui.composable


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cmpt370.group12.laptracker.R
import cmpt370.group12.laptracker.viewModel.ApplicationViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModeSelectScreen(viewModel: ApplicationViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly

    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(Color.LightGray),
            elevation = CardDefaults.elevatedCardElevation(),
            onClick = {
                viewModel.setMode(1)
            },
            content ={
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight= FontWeight.Bold,
                        fontSize = 30.sp,
                        text = "FreeStyle")

                    Image(modifier = Modifier
                        .size(100.dp),
                        painter = painterResource(id = R.drawable.runner),
                        contentDescription = ""
                    )
                    Text(modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight= FontWeight.Bold,
                        text = "Tap Start See Where Life Takes You")
                }

            }
        )

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(Color.LightGray),
            elevation = CardDefaults.elevatedCardElevation(),
            content ={
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight= FontWeight.Bold,
                        fontSize = 30.sp,
                        text = "Tracks")

                    Image(modifier = Modifier
                        .size(100.dp),
                        painter = painterResource(id = R.drawable.traintracks),
                        contentDescription = ""
                    )
                    Text(modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight= FontWeight.Bold,
                        text = "Create and Run you Own Tracks")
                }

            }
        )

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(Color.LightGray),
            elevation = CardDefaults.elevatedCardElevation(),
            content ={
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    Text(modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight= FontWeight.Bold,
                        fontSize = 20.sp,
                        text = "Statistics")

                    Image(modifier = Modifier
                        .size(120.dp),
                        painter = painterResource(id = R.drawable.statistics),
                        contentDescription = "ggg"
                    )
                    Text(modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight= FontWeight.Bold,
                        text = "You Know your Curious")
                }

            }
        )

    }

}
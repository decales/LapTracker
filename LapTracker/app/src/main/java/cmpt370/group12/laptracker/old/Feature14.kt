package cmpt370.group12.laptracker.old
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import cmpt370.group12.laptracker.view.theme.LapTrackerTheme

class Feature14 : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LapTrackerTheme {

            }
        }
    }
}
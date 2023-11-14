package cmpt370.group12.laptracker.old
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import cmpt370.group12.laptracker.Testing.Start_Test


@AndroidEntryPoint
class Feature8 : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
                 Start_Test()
                }
            }
}









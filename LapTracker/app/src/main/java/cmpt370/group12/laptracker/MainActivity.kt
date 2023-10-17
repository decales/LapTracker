package cmpt370.group12.laptracker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import android.content.Intent
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import cmpt370.group12.laptracker.ui.content.BottomNavigation
import cmpt370.group12.laptracker.ui.content.NavBarContent
import cmpt370.group12.laptracker.ui.content.NavigationView
import cmpt370.group12.laptracker.ui.theme.LapTrackerTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LapTrackerTheme {
                // A surface container using the 'background' color from the theme
                Column (
                    modifier = Modifier.fillMaxSize()
                ){
                    Spacer(modifier = Modifier.padding(1.dp))
                    Button(onClick = {
                        val intent = Intent(this@MainActivity, Feature1::class.java)
                        startActivity(intent)
                    }){
                        Text(text = "Feature1")
                    }
                    Spacer(modifier = Modifier.padding(1.dp))
                    Button(onClick = {
                        val intent = Intent(this@MainActivity, Feature2::class.java)
                        startActivity(intent)
                    }){
                        Text(text = "Feature2")
                    }
                    Spacer(modifier = Modifier.padding(1.dp))
                    Button(onClick = {
                        val intent = Intent(this@MainActivity, Feature3::class.java)
                        startActivity(intent)
                    }){
                        Text(text = "Feature3")
                    }
                    Spacer(modifier = Modifier.padding(1.dp))
                    Button(onClick = {
                        val intent = Intent(this@MainActivity, Feature4::class.java)
                        startActivity(intent)
                    }){
                        Text(text = "Feature4")
                    }
                    Spacer(modifier = Modifier.padding(1.dp))
                    Button(onClick = {
                        val intent = Intent(this@MainActivity, Feature5::class.java)
                        startActivity(intent)
                    }){
                        Text(text = "Feature5")
                    }
                    Spacer(modifier = Modifier.padding(1.dp))
                    Button(onClick = {
                        val intent = Intent(this@MainActivity, Feature6::class.java)
                        startActivity(intent)
                    }){
                        Text(text = "Feature6")
                    }
                    Spacer(modifier = Modifier.padding(1.dp))
                    Button(onClick = {
                        val intent = Intent(this@MainActivity, Feature7::class.java)
                        startActivity(intent)
                    }){
                        Text(text = "Feature7")
                    }
                    Spacer(modifier = Modifier.padding(1.dp))
                    Button(onClick = {
                        val intent = Intent(this@MainActivity, Feature8::class.java)
                        startActivity(intent)
                    }){
                        Text(text = "Feature8")
                    }
                    Spacer(modifier = Modifier.padding(1.dp))
                    Button(onClick = {
                        val intent = Intent(this@MainActivity, Feature9::class.java)
                        startActivity(intent)
                    }){
                        Text(text = "Feature9")
                    }
                    Spacer(modifier = Modifier.padding(1.dp))
                    Button(onClick = {
                        val intent = Intent(this@MainActivity, Feature10::class.java)
                        startActivity(intent)
                    }){
                        Text(text = "Feature10")
                    }
                    Spacer(modifier = Modifier.padding(1.dp))
                    Button(onClick = {
                        val intent = Intent(this@MainActivity, Feature11::class.java)
                        startActivity(intent)
                    }){
                        Text(text = "Feature11")
                    }
                    Spacer(modifier = Modifier.padding(1.dp))
                    Button(onClick = {
                        val intent = Intent(this@MainActivity, Feature12::class.java)
                        startActivity(intent)
                    }){
                        Text(text = "Feature12")
                    }
                    Spacer(modifier = Modifier.padding(1.dp))
                    Button(onClick = {
                        val intent = Intent(this@MainActivity, Feature13::class.java)
                        startActivity(intent)
                    }){
                        Text(text = "Feature13")
                    }
                    Spacer(modifier = Modifier.padding(1.dp))
                    Button(onClick = {
                        val intent = Intent(this@MainActivity, Feature14::class.java)
                        startActivity(intent)
                    }){
                        Text(text = "Feature14")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LapTrackerTheme {
        Greeting("Android")
    }
}








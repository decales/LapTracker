package com.test.lap_tracking_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var multiScreenVariable = 5
    lateinit var value: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val newTrackButton: Button = findViewById(R.id.newTrack)
        value = findViewById(R.id.multiScreenVar)
        value.text = "$multiScreenVariable"
        newTrackButton.setOnClickListener {
            // On button press change screens
            var intent = Intent(this@MainActivity, NewTrack::class.java)
            //Send data to other activity
            intent.putExtra("multiScreenVariable", multiScreenVariable)
            startActivity(intent)
        }

    }
}
package com.test.lap_tracking_app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NewTrack : AppCompatActivity() {
    lateinit var value: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_track)
        var multiScreenVariable = intent.getIntExtra("multiScreenVariable", 0)
        value = findViewById(R.id.multiScreenVar)
        value.text = "$multiScreenVariable"
    }
}
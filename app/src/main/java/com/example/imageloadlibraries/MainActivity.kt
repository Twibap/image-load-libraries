package com.example.imageloadlibraries

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val viewPicasso: ImageView = findViewById(R.id.viewPicasso)
        val viewGlide: ImageView = findViewById(R.id.viewGlide)
        findViewById<Button>(R.id.button).setOnClickListener {
            Log.i("onClick!!!", "onCreate: Load image")


            val host = "192.168.0.0"
            val uri = "http://$host/0020.jpg"
            Picasso.get()
                .load(uri)
                .placeholder(R.mipmap.ic_launcher)
                .into(viewPicasso)

            Glide.with(baseContext)
                .load(uri)
                .placeholder(R.mipmap.ic_launcher)
                .into(viewGlide)

        }
    }
}
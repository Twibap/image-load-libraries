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
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.net.CookieManager

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

            val host = "192.168.1.1"
//            val host = "172.30.1.206"
//        val host = "172.30.1.165"
//            val port = "8000"
        val port = "9000"
        val uri = "http://$host:$port/0020.jpg"

        val loggingInterceptor = HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger {
                Log.w("PicassoHttpClient", "LoggingInterceptor Logger : $it" )
            }
        ).apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        }

        val cookieJar = JavaNetCookieJar(CookieManager())

        val client = OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .addInterceptor(ETagInterceptor(cookieJar))
            .addInterceptor(HeaderInterceptor(cookieJar))
            .addInterceptor(loggingInterceptor)
            .build()

        val viewPicasso: ImageView = findViewById(R.id.viewPicasso)
        val viewGlide: ImageView = findViewById(R.id.viewGlide)
        findViewById<Button>(R.id.button).setOnClickListener {
            Log.i("onClick!!!", "onCreate: Load image")

            Picasso.Builder(baseContext)
                .downloader(OkHttp3Downloader(client))
                .build()
                .load(uri)
                .placeholder(R.mipmap.ic_launcher)
                .into(viewPicasso)

            Glide.with(baseContext)
                .load(uri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.mipmap.ic_launcher)
                .into(viewGlide)

        }
    }
}
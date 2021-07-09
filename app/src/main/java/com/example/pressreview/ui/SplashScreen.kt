package com.example.pressreview.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pressreview.MainActivity
import com.example.pressreview.R
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)



        MainScope().launch {
            delay(300L)
            startActivity(Intent(this@SplashScreen,MainActivity::class.java))
            finish()

        }
    }
}
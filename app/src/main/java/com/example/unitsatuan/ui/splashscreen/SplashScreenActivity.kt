package com.example.unitsatuan.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.unitsatuan.R
import com.example.unitsatuan.ui.login.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds


@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        lifecycleScope.launch {
            delay(3.seconds)
            startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
            finish()
        }
    }
}
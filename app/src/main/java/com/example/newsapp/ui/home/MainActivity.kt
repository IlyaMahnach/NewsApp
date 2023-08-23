package com.example.newsapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        val intent = Intent(this@MainActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()


    }
}
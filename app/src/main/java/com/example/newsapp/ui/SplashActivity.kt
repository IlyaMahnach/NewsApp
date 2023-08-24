package com.example.newsapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.newsapp.ui.home.NewsListActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        val intent = Intent(this@SplashActivity, NewsListActivity::class.java)
        startActivity(intent)
        finish()


    }
}
package com.example.newsapp

import android.app.Application
import android.content.Context
import com.example.newsapp.di.AppComponent
import com.example.newsapp.di.DaggerAppComponent

class NewsApplication : Application() {

    private var _appComponent: AppComponent? = null

    val appComponent: AppComponent
        get() = checkNotNull(_appComponent)

    override fun onCreate() {
        super.onCreate()
        _appComponent = DaggerAppComponent.builder()
            .application(this)
            .create()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is NewsApplication -> appComponent
        else -> (this.applicationContext as NewsApplication).appComponent
    }
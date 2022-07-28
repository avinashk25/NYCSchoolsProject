package com.example.nycschoolsproject

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The application class - an entry point into our app where we initialize Dagger.
 */
@HiltAndroidApp
class Application : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
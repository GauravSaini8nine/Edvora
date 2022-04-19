package com.example.edvora.app

import android.app.Application
import com.example.edvora.utils.ModelPreferencesManager

class Edvora : Application() {

    override fun onCreate() {
        super.onCreate()

        ModelPreferencesManager.with(this)
    }
}
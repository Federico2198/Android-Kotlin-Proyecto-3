package com.example.proyecto_3_federico_moreira

import android.app.Application
import com.google.firebase.FirebaseApp

class BaseApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
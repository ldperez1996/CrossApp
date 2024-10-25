package com.example.crossplan

import android.app.Application
import com.google.firebase.FirebaseApp

class CrossPlanApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inicializar Firebase cuando la aplicación se inicie
        FirebaseApp.initializeApp(this)
    }
}

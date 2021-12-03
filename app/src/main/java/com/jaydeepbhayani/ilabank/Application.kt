package com.jaydeepbhayani.ilabank

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase

class Application : Application() {

    companion object {
    }

    var context: Context? = null

    override fun onCreate() {
        super.onCreate()

        context = getApplicationContext();


        FirebaseApp.initializeApp(this)
        //FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(false)
    }

    fun getAppContext(): Context? {
        return context
    }

    fun getClientDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }
}
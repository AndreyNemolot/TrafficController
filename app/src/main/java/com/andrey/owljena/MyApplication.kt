package com.andrey.owljena

import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val assetManager = assets
        val istr = assetManager.open("4.owl")
        //val istr = assetManager.open("pizza.owl")
        OntologyProvider.instance.loadOntology(istr)

    }
}
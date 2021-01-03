package test.forest_interactive.presentation

import android.app.Application
import test.forest_interactive.data.db.CatDatabase

class CatApplication : Application() {
    companion object {
        lateinit var instance: CatApplication
        lateinit var database: CatDatabase
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        database = CatDatabase.invoke(this)
    }
}
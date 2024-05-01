package di

import android.app.Application
import android.content.Context

object AppContainer {

    lateinit var applicationContext: Context
        private set

    fun init(application: Application) {
        applicationContext = application.applicationContext
    }

}
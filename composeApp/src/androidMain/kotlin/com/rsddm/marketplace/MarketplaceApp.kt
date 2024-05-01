package com.rsddm.marketplace

import android.app.Application
import di.AppContainer

class MarketplaceApp : Application() {

    override fun onCreate() {
        super.onCreate()

        AppContainer.init(this)
    }
}
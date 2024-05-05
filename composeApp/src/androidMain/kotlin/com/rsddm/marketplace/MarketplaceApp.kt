package com.rsddm.marketplace

import android.app.Application
import bridge.ModuleBridge
import bridge.ProfileImpl
import di.AppContainer

class MarketplaceApp : Application() {

    override fun onCreate() {
        super.onCreate()

        AppContainer.init(this)

        ModuleBridge.buildBridge(
            ProfileImpl()
        )
    }
}
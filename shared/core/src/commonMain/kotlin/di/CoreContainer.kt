package di

import data.LocalStorage
import data.createDataStore
import network.Api

object CoreContainer {


    val api = Api()
    val localStorage: LocalStorage by lazy { LocalStorage(createDataStore()) }
}
package di

import data.LocalStorage
import data.LocalStorageFactory
import network.Api

object CoreContainer {
    val api = Api()
    val localStorage: LocalStorage by LocalStorageFactory()
}
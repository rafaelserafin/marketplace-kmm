package data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import di.AppContainer

actual fun createDataStore() : DataStore<Preferences> {
    return createDataStore(
        producePath = { AppContainer.applicationContext.filesDir.resolve(dataStoreFileName).absolutePath }
    )
}
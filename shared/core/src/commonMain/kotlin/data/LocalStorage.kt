package data

import Factory
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class LocalStorageFactory : Factory<LocalStorage>(){
    override fun provide(): LocalStorage {
        return LocalStorage(dataStore = createDataStore())
    }
}

class LocalStorage(val dataStore: DataStore<Preferences>) {

    suspend fun save(key: String, value: Serializable) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = Json.encodeToString(value)
        }
    }

    suspend fun save(key: String, value: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    suspend inline fun <reified T> get(key: String): T? {
        return dataStore.data.first()[stringPreferencesKey(key)]?.let { Json.decodeFromString<T>(it) }
    }

    suspend inline fun get(key: String): String? {
        return dataStore.data.first()[stringPreferencesKey(key)]
    }

}
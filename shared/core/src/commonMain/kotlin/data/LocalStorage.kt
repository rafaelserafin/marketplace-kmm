package data

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface LocalStorage {
    suspend fun delete(key: String)

    suspend fun getString(key: String): String?

    suspend fun saveString(key: String, value: String)
}

suspend inline fun <reified T> LocalStorage.save(key: String, value: T) =
    this.saveString(key, Json.encodeToString(value))

suspend inline fun <reified T> LocalStorage.get(key: String): T? =
    getString(key)?.let { Json.decodeFromString<T>(it) }
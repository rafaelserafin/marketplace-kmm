package com.rsddm.marketplace.navigation

import android.os.Bundle
import kotlinx.serialization.json.Json

inline fun <reified T> Bundle.getObject(key: String) : T {
    return getString(key)?.let { Json.decodeFromString<T>(it) } ?: throw Exception("invalid argument")
}
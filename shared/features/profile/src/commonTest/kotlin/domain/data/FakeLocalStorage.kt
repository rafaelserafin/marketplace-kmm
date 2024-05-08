package domain.data

import data.LocalStorage

class FakeLocalStorage : LocalStorage {

    private val map = mutableMapOf<String, String>()

    fun clear() {
        map.clear()
    }

    override suspend fun delete(key: String) {
        map.remove(key)
    }

    override suspend fun getString(key: String): String? {
        return map[key]
    }

    override suspend fun saveString(key: String, value: String) {
        map[key] = value
    }
}
package data

import domain.entities.Theme

interface DesignSystemRepository {

    suspend fun loadThemeLocal(theme: String) : Theme?

    suspend fun saveThemeLocal(name: String, theme: Theme)

    suspend fun getTheme(theme: String) : Theme

}
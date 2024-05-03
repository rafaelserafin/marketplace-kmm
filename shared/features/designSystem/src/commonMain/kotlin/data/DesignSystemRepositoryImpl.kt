package data

import data.api.DesignSystemApi
import domain.entities.Theme

private const val LOCAL_THEME_KEY = "local_theme_"

class DesignSystemRepositoryImpl(
    private val api: DesignSystemApi,
    private val localStorage: LocalStorage
) : DesignSystemRepository {
    override suspend fun loadThemeLocal(theme: String): Theme? {
        return localStorage.get<Theme>(LOCAL_THEME_KEY + theme)
    }

    override suspend fun saveThemeLocal(name: String, theme: Theme) {
        localStorage.save(LOCAL_THEME_KEY + name, theme)
    }

    override suspend fun getTheme(theme: String): Theme {
        return api.getTheme(theme)
    }
}
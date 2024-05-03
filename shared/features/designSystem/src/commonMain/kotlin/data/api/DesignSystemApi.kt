package data.api

import domain.entities.Theme


interface DesignSystemApi {
    suspend fun getTheme(theme: String) : Theme
}
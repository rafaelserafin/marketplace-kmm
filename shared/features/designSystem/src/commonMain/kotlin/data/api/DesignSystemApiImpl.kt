package data.api

import domain.entities.Theme
import network.Api

class DesignSystemApiImpl(private val api: Api) : DesignSystemApi {
    override suspend fun getTheme(theme: String): Theme {
        return api.get<Theme>("designSystem/theme=$theme")
    }

}
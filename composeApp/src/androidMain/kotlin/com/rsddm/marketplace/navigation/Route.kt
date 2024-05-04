package com.rsddm.marketplace.navigation

import java.util.UUID

abstract class Route(val route: String) {
    internal var routeID: UUID = UUID.randomUUID()
    internal open val from: Route? = null
}
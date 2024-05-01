package network

import kotlinx.serialization.json.Json
import network.backend.Backend
import network.backend.FakeBackend

/**
 * Fake api class
 *
 */
class Api(val api: Backend = FakeBackend()) {

    /**
     * Make a fake post request
     *
     * @param route is a fake request route
     * @param data is a fake data for request, in this case is not used
     *
     * @return is based on Backend interface
     */
    suspend inline fun <I, reified T> post(route: String, data: I) : T {
        return api.with(route)?.let { Json.decodeFromString<T>(it) } ?: throw Exception("unknown error")
    }

    /**
     * Make a fake get request
     *
     * @param route is a fake request route
     *
     * @return is based on Backend interface
     */
    suspend inline fun <reified T> get(route: String) : T {
        return api.with(route)?.let { Json.decodeFromString<T>(it) } ?: throw Exception("unknown error")
    }

}
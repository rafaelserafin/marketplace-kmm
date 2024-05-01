package network.backend



internal class FakeBackend : Backend{

    private val responses = mapOf(
        Pair("route", "response")
    )

    override fun with(route: String) = responses[route]
}
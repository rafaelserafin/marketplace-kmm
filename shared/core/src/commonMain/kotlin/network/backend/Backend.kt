package network.backend

interface Backend {

    fun with(route: String) : String?
}
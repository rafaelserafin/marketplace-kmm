import kotlin.reflect.KProperty

abstract class Factory<T> {
    abstract fun provide(): T

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return provide()
    }
}



package common

sealed interface Resource<T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Error<T>(val exception: Throwable?) : Resource<T>

    val isSuccess: Boolean
        get() = this is Success
}
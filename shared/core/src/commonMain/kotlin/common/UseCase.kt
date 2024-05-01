package common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class UseCase<I, R> {

    internal abstract suspend fun implementation(input: I): R

    suspend fun execute(input: I, flowCollector: FlowCollector<Resource<R>>) =
        flow<Resource<R>> { emit(Resource.Success(implementation(input))) }
            .catch { emit(Resource.Error(it.cause)) }
            .flowOn(Dispatchers.IO)
            .collect(flowCollector)
}
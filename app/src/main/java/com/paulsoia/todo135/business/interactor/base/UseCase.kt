package global.zakaz.stockman.domain.interactor.base

import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<Params, out Type> where Type: Any {

    // dispatchers
    private val UI = Dispatchers.Main
    private val BG = Dispatchers.Default

    // type for empty parameter
    class None

    // parameters

    protected var params: Params? = null

    fun withParams(params: Params) {
        this.params = params
    }

    // run

    @WorkerThread
    abstract suspend fun run() : Result<Type>

    @WorkerThread
    suspend fun runWithParams(params: Params) : Result<Type> {
        withParams(params)
        return run()
    }

    // invoke

    operator fun invoke(params: Params, onResult: (Result<Type>) -> Unit = {}) {
        withParams(params)
        val job = GlobalScope.async(BG) { run() }
        GlobalScope.launch(UI) { onResult(job.await()) }
    }

    operator fun invoke(onResult: (Result<Type>) -> Unit = {}) {
        val job = GlobalScope.async(BG) { run() }
        GlobalScope.launch(UI) { onResult(job.await()) }
    }

    // invoke + suspend

    suspend operator fun invoke(params: Params) : Result<Type> {
        withParams(params)
        return run()
    }

    suspend operator fun invoke(): Result<Type> {
        return run()
    }

}
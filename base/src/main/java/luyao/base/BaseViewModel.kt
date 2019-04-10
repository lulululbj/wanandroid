package luyao.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

/**
 * Created by luyao
 * on 2019/1/29 9:58
 */
open class BaseViewModel : ViewModel(), LifecycleObserver {

    val mException: MutableLiveData<Exception> = MutableLiveData()


    private fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {

        viewModelScope.launch { block() }

    }

    suspend fun <T> launchIO(block: suspend CoroutineScope.() -> T) {
        withContext(Dispatchers.IO) {
            block
        }
    }

    fun launch(tryBlock: suspend CoroutineScope.() -> Unit) {
        launchOnUI {
            tryCatch(tryBlock, {}, {}, true)
        }
    }


    fun launchOnUITryCatch(tryBlock: suspend CoroutineScope.() -> Unit,
                           catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
                           finallyBlock: suspend CoroutineScope.() -> Unit,
                           handleCancellationExceptionManually: Boolean
    ) {
        launchOnUI {
            tryCatch(tryBlock, catchBlock, finallyBlock, handleCancellationExceptionManually)
        }
    }

    fun launchOnUITryCatch(tryBlock: suspend CoroutineScope.() -> Unit,
                           handleCancellationExceptionManually: Boolean = false
    ) {
        launchOnUI {
            tryCatch(tryBlock, {}, {}, handleCancellationExceptionManually)
        }
    }


    private suspend fun tryCatch(
            tryBlock: suspend CoroutineScope.() -> Unit,
            catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
            finallyBlock: suspend CoroutineScope.() -> Unit,
            handleCancellationExceptionManually: Boolean = false) {
        coroutineScope {
            try {
                tryBlock()
            } catch (e: Exception) {
                if (e !is CancellationException || handleCancellationExceptionManually) {
                    mException.value = e
                    catchBlock(e)
                } else {
                    throw e
                }
            } finally {
                finallyBlock()
            }
        }
    }

    suspend fun executeResponse(response: WanResponse<Any>, successBlock: suspend CoroutineScope.() -> Unit,
                                errorBlock: suspend CoroutineScope.() -> Unit) {
        coroutineScope {
            if (response.errorCode == -1) errorBlock()
            else successBlock()
        }
    }

}
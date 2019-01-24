package luyao.wanandroid.ext

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by luyao
 * on 2019/1/24 16:03
 */
 fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
    CoroutineScope(Dispatchers.Main).launch { block() }

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
                       handleCancellationExceptionManually: Boolean=false
) {
    launchOnUI {
        tryCatch(tryBlock, {}, {}, handleCancellationExceptionManually)
    }
}


private suspend fun CoroutineScope.tryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean = false) {
    try {
        tryBlock()
    } catch (e: Throwable) {
        if (e !is CancellationException || handleCancellationExceptionManually) {
            catchBlock(e)
        } else {
            throw e
        }
    } finally {
        finallyBlock()
    }

}

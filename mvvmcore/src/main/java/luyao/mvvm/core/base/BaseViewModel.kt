package luyao.mvvm.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import luyao.mvvm.core.Result

/**
 * Created by luyao
 * on 2019/5/31 16:06
 */
open class BaseViewModel : ViewModel() {

    open class UiState<T>(
            val isLoading: Boolean = false,
            val isRefresh: Boolean = false,
            val isSuccess: T? = null,
            val isError: String?= null
    )


    open class BaseUiModel<T>(
            var showLoading: Boolean = false,
            var showError: String? = null,
            var showSuccess: T? = null,
            var showEnd: Boolean = false, // 加载更多
            var isRefresh: Boolean = false // 刷新

    )

    val mException: MutableLiveData<Throwable> = MutableLiveData()


    fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {

        viewModelScope.launch { block() }

    }

    suspend fun <T> launchOnIO(block: suspend CoroutineScope.() -> T) {
        withContext(Dispatchers.IO) {
            block
        }
    }
}
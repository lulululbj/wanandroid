package luyao.wanandroid.ui.square

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import luyao.util.ktx.base.BaseViewModel
import luyao.wanandroid.model.bean.ArticleList
import luyao.wanandroid.model.repository.SquareRepository
import luyao.wanandroid.util.Preference

/**
 * Created by luyao
 * on 2019/10/15 10:46
 */
class SquareViewModel : BaseViewModel() {

    private var isLogin by Preference(Preference.IS_LOGIN, false)
    private val _uiState = MutableLiveData<SquareUiModel>()
    val uiState: LiveData<SquareUiModel>
        get() = _uiState

    private val repository by lazy { SquareRepository() }

    private var currentPage = 0


    fun getSquareArticleList(isRefresh: Boolean = false) {
        viewModelScope.launch(Dispatchers.Default) {

            withContext(Dispatchers.Main) { emitUiState(true) }
            if (isRefresh) currentPage = 0
            val result = repository.getSquareArticleList(currentPage)

            withContext(Dispatchers.Main) {
                if (result is luyao.wanandroid.core.Result.Success) {
                    val articleList = result.data
                    if (articleList.offset >= articleList.total) {
                        emitUiState(showLoading = false, showEnd = true)
                        return@withContext
                    }
                    currentPage++
                    emitUiState(showLoading = false, showSuccess = articleList, isRefresh = isRefresh)

                } else if (result is luyao.wanandroid.core.Result.Error) {
                    emitUiState(showLoading = false, showError = result.exception.message)
                }

            }

        }
    }

    fun checkLogin() {
        emitUiState(needLogin = !isLogin)
    }

    private fun emitUiState(
            showLoading: Boolean = false,
            showError: String? = null,
            showSuccess: ArticleList? = null,
            showEnd: Boolean = false,
            isRefresh: Boolean = false,
            needLogin: Boolean? = null
    ) {
        val uiModel = SquareUiModel(showLoading, showError, showSuccess, showEnd, isRefresh,needLogin)
        _uiState.value = uiModel
    }


    data class SquareUiModel(
            val showLoading: Boolean,
            val showError: String?,
            val showSuccess: ArticleList?,
            val showEnd: Boolean, // 加载更多
            val isRefresh: Boolean, // 刷新
            val needLogin: Boolean? = null
    )


}
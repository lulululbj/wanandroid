package luyao.wanandroid.ui.system

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import luyao.util.ktx.base.BaseViewModel
import luyao.wanandroid.core.Result
import luyao.wanandroid.model.bean.ArticleList
import luyao.wanandroid.model.bean.SystemParent
import luyao.wanandroid.model.repository.CollectRepository
import luyao.wanandroid.model.repository.SystemRepository
import luyao.wanandroid.util.executeResponse

/**
 * Created by luyao
 * on 2019/4/8 16:40
 */
class SystemViewModel : BaseViewModel() {

    private val repository by lazy { SystemRepository() }
    private val collectRepository by lazy { CollectRepository() }

    private val _mSystemParentList: MutableLiveData<SystemUiModel> = MutableLiveData()
    val uiState: LiveData<SystemUiModel>
        get() = _mSystemParentList


    fun getSystemTypes() {
        viewModelScope.launch(Dispatchers.Main) {
            emitArticleUiState(showLoading = true)
            val result = withContext(Dispatchers.IO) { repository.getSystemTypes() }
            if (result is Result.Success)
                emitArticleUiState(showLoading = false, showSuccess = result.data)
            else if (result is Result.Error)
                emitArticleUiState(showLoading = false, showError = result.exception.message)
        }
    }

    fun collectArticle(articleId: Int, boolean: Boolean) {
        launch {
            withContext(Dispatchers.IO) {
                if (boolean) collectRepository.collectArticle(articleId)
                else collectRepository.unCollectArticle(articleId)
            }
        }
    }

    private fun emitArticleUiState(
            showLoading: Boolean = false,
            showError: String? = null,
            showSuccess: List<SystemParent>? = null
    ) {
        val uiModel = SystemUiModel(showLoading, showError, showSuccess)
        _mSystemParentList.value = uiModel
    }

    class SystemUiModel(showLoading: Boolean,
                        showError: String?,
                        showSuccess: List<SystemParent>?) : BaseUiModel<List<SystemParent>>(showLoading, showError, showSuccess)

    open class BaseUiModel<T>(
            val showLoading: Boolean = false,
            val showError: String? = null,
            val showSuccess: T? = null,
            val showEnd: Boolean = false, // 加载更多
            val isRefresh: Boolean = false // 刷新

    )
}
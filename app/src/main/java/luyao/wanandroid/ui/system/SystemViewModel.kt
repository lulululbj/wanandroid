package luyao.wanandroid.ui.system

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import luyao.mvvm.core.Result
import luyao.mvvm.core.base.BaseViewModel
import luyao.wanandroid.model.bean.SystemParent
import luyao.wanandroid.model.repository.CollectRepository
import luyao.wanandroid.model.repository.SystemRepository
import javax.inject.Inject

/**
 * Created by luyao
 * on 2019/4/8 16:40
 */
@HiltViewModel
class SystemViewModel @Inject constructor(
    private val collectRepository: CollectRepository
) : BaseViewModel() {

    val listState: LazyListState = LazyListState()
    private val _mSystemParentList: MutableLiveData<BaseUiModel<List<SystemParent>>> =
        MutableLiveData()
    val uiState: LiveData<BaseUiModel<List<SystemParent>>>
        get() = _mSystemParentList


    fun getSystemTypes() {
        viewModelScope.launch(Dispatchers.Main) {
            emitArticleUiState(showLoading = true)
            val result = withContext(Dispatchers.IO) { SystemRepository.getSystemTypes() }
            if (result is Result.Success)
                emitArticleUiState(showLoading = false, showSuccess = result.data)
            else if (result is Result.Error)
                emitArticleUiState(showLoading = false, showError = result.exception.message)
        }
    }

    fun collectArticle(articleId: Int, boolean: Boolean) {
        launchOnUI {
            withContext(Dispatchers.IO) {
                if (boolean) collectRepository.collectArticle(articleId)
                else collectRepository.unCollectArticle(articleId)
            }
        }
    }

    private fun emitArticleUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        showSuccess: List<SystemParent>? = null,
    ) {
        val uiModel = BaseUiModel(showLoading, showError, showSuccess)
        _mSystemParentList.value = uiModel
    }
}
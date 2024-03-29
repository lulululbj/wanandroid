package luyao.wanandroid.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import luyao.wanandroid.base.BaseViewModel
import luyao.wanandroid.model.bean.ArticleList
import luyao.wanandroid.model.bean.Hot
import luyao.wanandroid.model.bean.Result
import luyao.wanandroid.model.repository.CollectRepository
import luyao.wanandroid.model.repository.SearchRepository
import javax.inject.Inject

/**
 * Created by luyao
 * on 2019/4/8 15:29
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val collectRepository: CollectRepository
) : BaseViewModel() {

    private var currentPage = 0

    private val _uiState = MutableLiveData<SearchUiModel>()
    val uiState: LiveData<SearchUiModel>
        get() = _uiState

    private val _hotState = MutableLiveData<List<Hot>>()
    val hotState: LiveData<List<Hot>>
        get() = _hotState

    private val _webSiteState = MutableLiveData<List<Hot>>()
    val webSite: LiveData<List<Hot>>
        get() = _webSiteState


    fun searchHot(isRefresh: Boolean = false, key: String) {
        if (key.isEmpty()) return
        viewModelScope.launch(Dispatchers.Default) {

            withContext(Dispatchers.Main) { emitArticleUiState(showLoading = true) }
            if (isRefresh) currentPage = 0

            val result = searchRepository.searchHot(currentPage, key)

            withContext(Dispatchers.Main) {
                if (result is Result.Success) {
                    val articleList = result.data
                    if (articleList.offset >= articleList.total) {
                        if (articleList.offset > 0)
                            emitArticleUiState(showLoading = false, showEnd = true)
                        else
                            emitArticleUiState(
                                showLoading = false,
                                isRefresh = true,
                                showSuccess = ArticleList(0, 0, 0, 0, 0, false, emptyList())
                            )
                        return@withContext
                    }
                    currentPage++
                    emitArticleUiState(
                        showLoading = false,
                        showSuccess = articleList,
                        isRefresh = isRefresh
                    )

                } else if (result is Result.Error) {
                    emitArticleUiState(showLoading = false, showError = result.exception.message)
                }

            }

        }
    }


    fun getWebSites() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { searchRepository.getWebSites() }
            if (result is Result.Success) {
                _webSiteState.value = result.data
            }
        }
    }

    fun getHotSearch() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { searchRepository.getHotSearch() }
            if (result is Result.Success) {
                _hotState.value = result.data
            }
        }
    }

    fun collectArticle(articleId: Int, boolean: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                if (boolean) collectRepository.collectArticle(articleId)
                else collectRepository.unCollectArticle(articleId)
            }
        }
    }


    private fun emitArticleUiState(
        showHot: Boolean = false,
        showLoading: Boolean = false,
        showError: String? = null,
        showSuccess: ArticleList? = null,
        showEnd: Boolean = false,
        isRefresh: Boolean = false,
        showWebSites: List<Hot>? = null,
        showHotSearch: List<Hot>? = null
    ) {
        val uiModel = SearchUiModel(
            showHot,
            showLoading,
            showError,
            showSuccess,
            showEnd,
            isRefresh,
            showWebSites,
            showHotSearch
        )
        _uiState.value = uiModel
    }


    data class SearchUiModel(
        val showHot: Boolean,
        val showLoading: Boolean,
        val showError: String?,
        val showSuccess: ArticleList?,
        val showEnd: Boolean, // 加载更多
        val isRefresh: Boolean, // 刷新
        val showWebSites: List<Hot>?,
        val showHotSearch: List<Hot>?
    )

}
package luyao.wanandroid.ui.square

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import luyao.wanandroid.base.BaseViewModel
import luyao.wanandroid.model.bean.Article
import luyao.wanandroid.model.bean.ArticleList
import luyao.wanandroid.model.bean.Banner
import luyao.wanandroid.model.bean.Result
import luyao.wanandroid.model.repository.*
import javax.inject.Inject

/**
 * Created by luyao
 * on 2019/10/15 10:46
 */
@HiltViewModel
open class ArticleViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var squareRepository: SquareRepository

    @Inject
    lateinit var homeRepository: HomeRepository

    @Inject
    lateinit var collectRepository: CollectRepository

    @Inject
    lateinit var searchRepository: SearchRepository


    sealed class ArticleType {
        object Home : ArticleType()                 // 首页
        object Question : ArticleType()                 // 首页
        object Square : ArticleType()               // 广场
        object LatestProject : ArticleType()        // 最新项目
        object ProjectDetailList : ArticleType()    // 项目列表
        object Collection : ArticleType()           // 收藏
        object SystemType : ArticleType()           // 体系分类
        object Blog : ArticleType()                 // 公众号
        object Search : ArticleType()                 // 搜索
    }

    private val _uiState = MutableLiveData<ArticleUiModel>()
    val uiState: LiveData<ArticleUiModel>
        get() = _uiState

    private var currentPage = 0

    val lazyListState = LazyListState()
    private val allArticleList = arrayListOf<Article>()


//    val mBanners: LiveData<List<Banner>> = liveData {
//        kotlin.runCatching {
//            val data = homeRepository.getBanners()
//            if (data is Result.Success) emit(data.data)
//        }
//    }


    val refreshSquare: () -> Unit = { getSquareArticleList(true) }
    val refreshCollect: () -> Unit = { getCollectArticleList(true) }
    val refreshHome: () -> Unit = { getHomeArticleList(true) }

    fun getHomeArticleList(isRefresh: Boolean = false) = getArticleList(ArticleType.Home, isRefresh)
    fun getQuestionList(isRefresh: Boolean = false) =
        getArticleList(ArticleType.Question, isRefresh)

    fun getSquareArticleList(isRefresh: Boolean = false) =
        getArticleList(ArticleType.Square, isRefresh)

    fun getLatestProjectList(isRefresh: Boolean = false) =
        getArticleList(ArticleType.LatestProject, isRefresh)

    fun getProjectTypeDetailList(isRefresh: Boolean = false, cid: Int) =
        getArticleList(ArticleType.ProjectDetailList, isRefresh, cid)

    fun getCollectArticleList(isRefresh: Boolean = false) =
        getArticleList(ArticleType.Collection, isRefresh)

    fun getSystemTypeArticleList(isRefresh: Boolean = false, cid: Int) =
        getArticleList(ArticleType.SystemType, isRefresh, cid)

    fun getBlogArticleList(isRefresh: Boolean = false, cid: Int) =
        getArticleList(ArticleType.Blog, isRefresh, cid)

    fun collectArticle(articleId: Int, boolean: Boolean) {
        launchOnUI {
            withContext(Dispatchers.IO) {
                if (boolean) collectRepository.collectArticle(articleId)
                else collectRepository.unCollectArticle(articleId)
            }
        }
    }

    fun searchArticle(isRefresh: Boolean = false, key: String) =
        getArticleList(ArticleType.Search, isRefresh, key = key)

    private fun getArticleList(
        articleType: ArticleType,
        isRefresh: Boolean = false,
        cid: Int = 0,
        key: String = ""
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            emitArticleUiState(isRefresh, showSuccess = allArticleList, showEnd = true)
            if (isRefresh) {
                allArticleList.clear()
                currentPage = if (articleType is ArticleType.ProjectDetailList) 1 else 0
            }

            val result: Result<ArticleList> = when (articleType) {
                ArticleType.Home -> homeRepository.getArticleList(currentPage)
                ArticleType.Question -> homeRepository.getQuestionList(currentPage)
                ArticleType.Square -> squareRepository.getSquareArticleList(currentPage)
                ArticleType.LatestProject -> ProjectRepository.getLastedProject(currentPage)
                ArticleType.ProjectDetailList -> ProjectRepository.getProjectTypeDetailList(
                    currentPage,
                    cid
                )
                ArticleType.Collection -> collectRepository.getCollectArticles(currentPage)
                ArticleType.SystemType -> SystemRepository.getSystemTypeDetail(cid, currentPage)
                ArticleType.Blog -> SystemRepository.getBlogArticle(cid, currentPage)
                ArticleType.Search -> searchRepository.searchHot(currentPage, key)
            }

            if (result is Result.Success) {
                val articleList = result.data
                if (articleList.offset >= articleList.total || articleList.datas.size == articleList.total) {
                    emitArticleUiState(
                        showLoading = false,
                        showEnd = true,
                        showSuccess = allArticleList.apply { addAll(articleList.datas) })
                    return@launch
                }
                currentPage++
                emitArticleUiState(
                    showLoading = false,
                    showSuccess = allArticleList.apply { addAll(articleList.datas) },
                    isRefresh = isRefresh
                )
            } else if (result is Result.Error) {
                emitArticleUiState(showLoading = false, showError = result.exception.message)
            }
        }
    }

    private fun emitArticleUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        showSuccess: List<Article>? = null,
        showEnd: Boolean = false,
        isRefresh: Boolean = false,
        needLogin: Boolean? = null
    ) {
        val uiModel =
            ArticleUiModel(showLoading, showError, showSuccess, showEnd, isRefresh, needLogin)
        _uiState.value = uiModel
    }


    data class ArticleUiModel(
        val showLoading: Boolean,
        val showError: String?,
        val showSuccess: List<Article>?,
        val showEnd: Boolean, // 加载更多
        val isRefresh: Boolean, // 刷新
        val needLogin: Boolean? = null,
        val listState: LazyListState = LazyListState()
    )


}
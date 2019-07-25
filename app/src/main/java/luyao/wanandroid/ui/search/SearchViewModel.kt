package luyao.wanandroid.ui.search

import androidx.lifecycle.MutableLiveData
import executeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import luyao.util.ktx.base.BaseViewModel
import luyao.wanandroid.model.bean.ArticleList
import luyao.wanandroid.model.bean.Hot
import luyao.wanandroid.model.repository.SearchRepository

/**
 * Created by luyao
 * on 2019/4/8 15:29
 */
class SearchViewModel : BaseViewModel() {

    private val repository by lazy { SearchRepository() }
    val mArticleList: MutableLiveData<ArticleList> = MutableLiveData()
    val mWebSiteHot: MutableLiveData<List<Hot>> = MutableLiveData()
    val mHotSearch: MutableLiveData<List<Hot>> = MutableLiveData()

    fun searchHot(page: Int, key: String) {
        launch {
            val result = withContext(Dispatchers.IO) { repository.searchHot(page, key) }
            executeResponse(result, { mArticleList.value = result.data }, {})
        }
    }

    fun getWebSites() {
        launch {
            val result = withContext(Dispatchers.IO) { repository.getWebSites() }
            executeResponse(result, { mWebSiteHot.value = result.data }, {})
        }
    }

    fun getHotSearch() {
        launch {
            val result = withContext(Dispatchers.IO) { repository.getHotSearch() }
            executeResponse(result, { mHotSearch.value = result.data }, {})
        }
    }

    fun collectArticle(articleId: Int, boolean: Boolean) {
        launch {
            withContext(Dispatchers.IO) {
                if (boolean) repository.collectArticle(articleId)
                else repository.unCollectArticle(articleId)
            }
        }
    }

}
package luyao.wanandroid.ui.collect

import androidx.lifecycle.MutableLiveData
import executeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import luyao.util.ktx.base.BaseViewModel
import luyao.wanandroid.model.repository.CollectRepository
import luyao.wanandroid.model.bean.ArticleList

/**launch
 * Created by luyao
 * on 2019/4/8 16:03
 */
class CollectViewModel : BaseViewModel() {

    val mArticleList: MutableLiveData<ArticleList> = MutableLiveData()
    val mErrorMsg: MutableLiveData<String> = MutableLiveData()
    private val repository by lazy { CollectRepository() }

    fun getCollectArticles(page: Int) {
        launch {
            val result = withContext(Dispatchers.IO) { repository.getCollectArticles(page) }
            executeResponse(result, { mArticleList.value = result.data }, { mErrorMsg.value = result.errorMsg })
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
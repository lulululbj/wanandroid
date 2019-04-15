package luyao.wanandroid.ui.project

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import luyao.base.BaseViewModel
import luyao.wanandroid.model.repository.ProjectRepository
import luyao.wanandroid.model.bean.ArticleList
import luyao.wanandroid.model.bean.SystemParent

/**
 * Created by luyao
 * on 2019/4/8 16:28
 */
class ProjectViewModel : BaseViewModel() {

    private val repository by lazy { ProjectRepository() }
    val mArticleList: MutableLiveData<ArticleList> = MutableLiveData()
    val mSystemParentList: MutableLiveData<List<SystemParent>> = MutableLiveData()

    fun getProjectTypeDetailList(page: Int, cid: Int) {
        launch{
            val result = withContext(Dispatchers.IO) { repository.getProjectTypeDetailList(page, cid) }
            executeResponse(result, { mArticleList.value = result.data }, {})
        }
    }

    fun getProjectTypeList() {
        launch{
            val result = withContext(Dispatchers.IO) { repository.getProjectTypeList() }
            executeResponse(result, { mSystemParentList.value = result.data }, {})
        }
    }

    fun collectArticle(articleId: Int, boolean: Boolean) {
        launch {
            val result = withContext(Dispatchers.IO) {
                if (boolean) repository.collectArticle(articleId)
                else repository.unCollectArticle(articleId)
            }
        }
    }
}
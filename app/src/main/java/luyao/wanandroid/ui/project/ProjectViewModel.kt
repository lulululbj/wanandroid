package luyao.wanandroid.ui.project

import androidx.lifecycle.MutableLiveData
import executeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import luyao.util.ktx.base.BaseViewModel
import luyao.wanandroid.model.bean.ArticleList
import luyao.wanandroid.model.bean.SystemParent
import luyao.wanandroid.model.repository.ProjectRepository

/**
 * Created by luyao
 * on 2019/4/8 16:28
 */
class ProjectViewModel : BaseViewModel() {

    private val repository by lazy { ProjectRepository() }
    val mArticleList: MutableLiveData<ArticleList> = MutableLiveData()
    val mSystemParentList: MutableLiveData<List<SystemParent>> = MutableLiveData()

    fun getProjectTypeDetailList(page: Int, cid: Int) {
        launch {
            val result = withContext(Dispatchers.IO) { repository.getProjectTypeDetailList(page, cid) }
            executeResponse(result, { mArticleList.value = result.data }, {})
        }
    }

    fun getProjectTypeList() {
        launch {
            val result = withContext(Dispatchers.IO) { repository.getProjectTypeList() }
            executeResponse(result, { mSystemParentList.value = result.data }, {})
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

    fun getLastedProject(page: Int) {
        launch {
            val result = withContext(Dispatchers.IO) { repository.getLastedProject(page) }
            executeResponse(result, { mArticleList.value = result.data }, {})
        }
    }

    fun getBlogType() {
        launch {
            val result = withContext(Dispatchers.IO) { repository.getBlog() }
            executeResponse(result, { mSystemParentList.value = result.data }, {})
        }
    }
}
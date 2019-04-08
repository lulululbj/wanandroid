package luyao.wanandroid.ui.project

import android.arch.lifecycle.MutableLiveData
import luyao.base.BaseViewModel
import luyao.wanandroid.api.WanRetrofitClient
import luyao.wanandroid.bean.ArticleList
import luyao.wanandroid.bean.SystemParent
import luyao.wanandroid.ext.launchOnUITryCatch

/**
 * Created by luyao
 * on 2019/4/8 16:28
 */
class ProjectViewModel : BaseViewModel() {

    val mArticleList: MutableLiveData<ArticleList> = MutableLiveData()
    val mSystemParentList: MutableLiveData<List<SystemParent>> = MutableLiveData()

    fun getProjectTypeDetailList(page: Int, cid: Int) {

        launchOnUITryCatch({
            val result = WanRetrofitClient.service.getProjectTypeDetail(page, cid).await()
            mArticleList.value = result.data
        }, {}, {}, true)

    }

    fun getProjectTypeList() {
        launchOnUITryCatch({
            val result = WanRetrofitClient.service.getProjectType().await()
            mSystemParentList.value = result.data
        }, {}, {}, true)
    }
}
package luyao.wanandroid.ui.system

import android.arch.lifecycle.MutableLiveData
import luyao.base.BaseViewModel
import luyao.wanandroid.api.WanRetrofitClient
import luyao.wanandroid.bean.ArticleList
import luyao.wanandroid.bean.SystemParent
import luyao.wanandroid.ext.launchOnUITryCatch

/**
 * Created by luyao
 * on 2019/4/8 16:40
 */
class SystemViewModel : BaseViewModel() {

    val mArticleList: MutableLiveData<ArticleList> = MutableLiveData()
    val mSystemParentList: MutableLiveData<List<SystemParent>> = MutableLiveData()


    fun getSystemTypeDetail(id: Int, page: Int) {

        launchOnUITryCatch({
            val result = WanRetrofitClient.service.getSystemTypeDetail(page, id).await()
            mArticleList.value = result.data
        }, {}, {}, true)
    }

    fun getSystemTypes() {
        launchOnUITryCatch({
            val result = WanRetrofitClient.service.getSystemType().await()
            mSystemParentList.value = result.data
        }, {}, {}, true)


    }
}
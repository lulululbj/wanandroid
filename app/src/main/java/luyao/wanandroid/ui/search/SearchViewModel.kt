package luyao.wanandroid.ui.search

import android.arch.lifecycle.MutableLiveData
import luyao.base.BaseViewModel
import luyao.wanandroid.api.WanRetrofitClient
import luyao.wanandroid.bean.ArticleList
import luyao.wanandroid.bean.Hot
import luyao.wanandroid.ext.launchOnUITryCatch

/**
 * Created by luyao
 * on 2019/4/8 15:29
 */
class SearchViewModel : BaseViewModel() {

    val mArticleList: MutableLiveData<ArticleList> = MutableLiveData()
    val mWebSiteHot: MutableLiveData<List<Hot>> = MutableLiveData()
    val mHotSearch: MutableLiveData<List<Hot>> = MutableLiveData()

    fun searchHot(page: Int, key: String) {
        launchOnUITryCatch({
            val result = WanRetrofitClient.service.searchHot(page, key).await()
            mArticleList.value = result.data
        }, {}, {}, true)
    }

    fun getWebSites() {
        launchOnUITryCatch({
            val result = WanRetrofitClient.service.getWebsites().await()
            mWebSiteHot.value = result.data
        }, {}, {}, true)
    }

    fun getHotSearch() {
        launchOnUITryCatch({
            val result = WanRetrofitClient.service.getHot().await()
            mHotSearch.value = result.data
        }, {}, {}, true)
    }
}
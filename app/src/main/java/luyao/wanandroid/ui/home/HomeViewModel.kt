package luyao.wanandroid.ui.home

import android.arch.lifecycle.MutableLiveData
import luyao.base.BaseViewModel
import luyao.wanandroid.api.WanRetrofitClient
import luyao.wanandroid.bean.ArticleList
import luyao.wanandroid.bean.Banner
import luyao.wanandroid.ext.launchOnUITryCatch

/**
 * Created by luyao
 * on 2019/1/29 10:27
 */
class HomeViewModel : BaseViewModel() {


    val mBanners: MutableLiveData<List<Banner>> = MutableLiveData()
    val mArticleList: MutableLiveData<ArticleList> = MutableLiveData()

    fun getBanners() {
        launchOnUITryCatch({
            val result = WanRetrofitClient.service.getBanner()
            mBanners.value = result.await().data
        }, {}, {}, true)
    }

    fun getArticleList(page: Int) {
        launchOnUITryCatch({
            val result = WanRetrofitClient.service.getHomeArticles(page)
            mArticleList.value = result.await().data
        }, {}, {}, true)
    }

    fun collectArticle(articleId: Int) {
        launchOnUITryCatch({
            val result=WanRetrofitClient.service.collectArticle(articleId)
            result.await()
        }, {}, {}, true)
    }

}
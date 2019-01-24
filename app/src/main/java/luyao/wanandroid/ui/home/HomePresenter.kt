package luyao.wanandroid.ui.home

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import luyao.wanandroid.api.WanRetrofitClient
import luyao.wanandroid.bean.Article
import luyao.wanandroid.ext.launchOnUITryCatch

/**
 * Created by luyao
 * on 2018/3/13 15:05
 */
class HomePresenter(
        private val mView: HomeContract.View
) : HomeContract.Presenter {

    init {
        mView.mPresenter = this
    }

    override fun start() {

    }

    override fun getBanners() {
        launchOnUITryCatch({
            val result = WanRetrofitClient.service.getBanner().await()
            mView.getBanner(result.data)
        })
    }

    override fun getArticles(page: Int) {
        launchOnUITryCatch({
            val result = WanRetrofitClient.service.getHomeArticles(page).await()
            mView.getArticles(result.data)
        })
    }

    override fun collectArticle(article: Article) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = WanRetrofitClient.service.collectArticle(article.id).await()
            mView.collectArticle(article)
        }
    }

    override fun cancelCollectArticle(article: Article) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = WanRetrofitClient.service.cancelCollectArticle(article.id).await()
            mView.cancleCollectArticle(article)
        }
    }
}
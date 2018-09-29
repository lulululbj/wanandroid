package luyao.wanandroid.ui.home

import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import luyao.wanandroid.api.WanRetrofitClient
import luyao.wanandroid.bean.Article
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by luyao
 * on 2018/3/13 15:05
 */
class HomePresenter(
        private val mView: HomeContract.View,
        private val uiContext: CoroutineContext = UI
) : HomeContract.Presenter {

    init {
        mView.mPresenter = this
    }

    override fun start() {

    }

    override fun getBanners() {
        launch(uiContext) {
            val result = WanRetrofitClient.service.getBanner().await()
            mView.getBanner(result.data)
        }
    }

    override fun getArticles(page: Int) {
        launch(uiContext) {
            val result = WanRetrofitClient.service.getHomeArticles(page).await()
            mView.getArticles(result.data)
        }
    }

    override fun collectArticle(article: Article) {
        launch(uiContext) {
            val result = WanRetrofitClient.service.collectArticle(article.id).await()
            mView.collectArticle(article)
        }
    }

    override fun cancelCollectArticle(article: Article) {
        launch(uiContext) {
            val result = WanRetrofitClient.service.cancelCollectArticle(article.id).await()
            mView.cancleCollectArticle(article)
        }
    }
}
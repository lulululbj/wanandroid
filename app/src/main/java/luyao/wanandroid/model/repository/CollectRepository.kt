package luyao.wanandroid.model.repository

import luyao.wanandroid.core.Result
import luyao.wanandroid.model.api.BaseRepository
import luyao.wanandroid.model.api.WanRetrofitClient
import luyao.wanandroid.model.bean.ArticleList

/**
 * Created by luyao
 * on 2019/4/10 14:01
 */
class CollectRepository : BaseRepository() {

    suspend fun getCollectArticles(page: Int): Result<ArticleList> {
        return safeApiCall(call = { requestCollectArticles(page) }, errorMessage = "网络错误")
    }

    suspend fun collectArticle(articleId: Int): Result<ArticleList> {
        return safeApiCall(call = { requestCollectArticle(articleId) }, errorMessage = "网络错误")
    }

    suspend fun unCollectArticle(articleId: Int): Result<ArticleList> {
        return safeApiCall(call = { requestCancelCollectArticle(articleId) }, errorMessage = "网络错误")
    }

    private suspend fun requestCollectArticles(page: Int): Result<ArticleList> =
            executeResponse(WanRetrofitClient.service.getCollectArticles(page))

    private suspend fun requestCollectArticle(articleId: Int): Result<ArticleList> =
            executeResponse(WanRetrofitClient.service.collectArticle(articleId))

    private suspend fun requestCancelCollectArticle(articleId: Int): Result<ArticleList> =
            executeResponse(WanRetrofitClient.service.cancelCollectArticle(articleId))
}
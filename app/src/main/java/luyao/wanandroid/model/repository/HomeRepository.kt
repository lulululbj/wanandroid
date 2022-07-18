package luyao.wanandroid.model.repository

import luyao.mvvm.core.Result
import luyao.wanandroid.model.api.BaseRepository
import luyao.wanandroid.model.api.WanRetrofitClient
import luyao.wanandroid.model.bean.ArticleList
import luyao.wanandroid.model.bean.Banner
import javax.inject.Inject

/**
 * Created by luyao
 * on 2019/4/10 14:09
 */
class HomeRepository @Inject constructor(): BaseRepository() {

    suspend fun getBanners(): Result<List<Banner>> {
        return safeApiCall(call = {requestBanners()},errorMessage = "")
    }

    private suspend fun requestBanners(): Result<List<Banner>> =
        executeResponse(WanRetrofitClient.service.getBanner())


    suspend fun getArticleList(page: Int): Result<ArticleList> {
        return safeApiCall(call = { requestArticleList(page) }, errorMessage = "")
    }

    private suspend fun requestArticleList(page: Int): Result<ArticleList> =
            executeResponse(WanRetrofitClient.service.getHomeArticles(page))
}
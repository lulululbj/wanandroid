package luyao.wanandroid.model.repository

import luyao.wanandroid.model.api.BaseRepository
import luyao.wanandroid.model.api.WanRetrofitClient
import luyao.wanandroid.model.bean.ArticleList
import luyao.wanandroid.model.bean.Banner
import luyao.wanandroid.model.bean.WanResponse

/**
 * Created by luyao
 * on 2019/4/10 14:09
 */
class HomeRepository : BaseRepository() {

    suspend fun getBanners(): WanResponse<List<Banner>> {
        return apiCall { WanRetrofitClient.service.getBanner() }
    }

    suspend fun getArticleList(page: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.getHomeArticles(page) }
    }

    suspend fun collectArticle(articleId: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.collectArticle(articleId) }
    }

    suspend fun unCollectArticle(articleId: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.cancelCollectArticle(articleId) }
    }
}
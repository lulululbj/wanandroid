package luyao.wanandroid.model.repository

import luyao.wanandroid.model.api.BaseRepository
import luyao.wanandroid.model.api.WanRetrofitClient
import luyao.wanandroid.model.bean.ArticleList
import luyao.wanandroid.model.bean.Hot
import luyao.wanandroid.model.bean.WanResponse

/**
 * Created by luyao
 * on 2019/4/10 14:26
 */
class SearchRepository : BaseRepository() {
    suspend fun searchHot(page: Int, key: String): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.searchHot(page, key) }
    }

    suspend fun getWebSites(): WanResponse<List<Hot>> {
        return apiCall { WanRetrofitClient.service.getWebsites() }
    }

    suspend fun getHotSearch(): WanResponse<List<Hot>> {
        return apiCall { WanRetrofitClient.service.getHot() }
    }

    suspend fun collectArticle(articleId: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.collectArticle(articleId) }
    }

    suspend fun unCollectArticle(articleId: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.cancelCollectArticle(articleId) }
    }
}
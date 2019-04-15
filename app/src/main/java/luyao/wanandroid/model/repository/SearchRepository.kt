package luyao.wanandroid.model.repository

import luyao.base.BaseRepository
import luyao.base.WanResponse
import luyao.wanandroid.model.api.WanRetrofitClient
import luyao.wanandroid.model.bean.ArticleList
import luyao.wanandroid.model.bean.Hot

/**
 * Created by luyao
 * on 2019/4/10 14:26
 */
class SearchRepository : BaseRepository() {
    suspend fun searchHot(page: Int, key: String): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.searchHot(page, key).await() }
    }

    suspend fun getWebSites(): WanResponse<List<Hot>> {
        return apiCall { WanRetrofitClient.service.getWebsites().await() }
    }

    suspend fun getHotSearch(): WanResponse<List<Hot>> {
        return apiCall { WanRetrofitClient.service.getHot().await() }
    }

    suspend fun collectArticle(articleId: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.collectArticle(articleId).await() }
    }

    suspend fun unCollectArticle(articleId: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.cancelCollectArticle(articleId).await() }
    }
}
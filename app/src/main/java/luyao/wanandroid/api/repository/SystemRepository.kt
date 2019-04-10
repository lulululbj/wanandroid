package luyao.wanandroid.api.repository

import luyao.base.BaseRepository
import luyao.base.WanResponse
import luyao.wanandroid.api.WanRetrofitClient
import luyao.wanandroid.bean.ArticleList
import luyao.wanandroid.bean.SystemParent

/**
 * Created by luyao
 * on 2019/4/10 14:34
 */
class SystemRepository : BaseRepository() {

    suspend fun getSystemTypeDetail(id: Int, page: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.getSystemTypeDetail(page, id).await() }
    }

    suspend fun getSystemTypes(): WanResponse<List<SystemParent>> {
        return apiCall { WanRetrofitClient.service.getSystemType().await() }
    }

    suspend fun collectArticle(articleId: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.collectArticle(articleId).await() }
    }

    suspend fun unCollectArticle(articleId: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.cancelCollectArticle(articleId).await() }
    }
}
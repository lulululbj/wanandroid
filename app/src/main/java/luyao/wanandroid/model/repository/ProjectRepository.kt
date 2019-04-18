package luyao.wanandroid.model.repository

import luyao.base.BaseRepository
import luyao.base.WanResponse
import luyao.wanandroid.model.api.WanRetrofitClient
import luyao.wanandroid.model.bean.ArticleList
import luyao.wanandroid.model.bean.SystemParent

/**
 * Created by luyao
 * on 2019/4/10 14:18
 */
class ProjectRepository : BaseRepository() {

    suspend fun getProjectTypeDetailList(page: Int, cid: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.getProjectTypeDetail(page, cid).await() }
    }

    suspend fun getProjectTypeList(): WanResponse<List<SystemParent>> {
        return apiCall { WanRetrofitClient.service.getProjectType().await() }
    }

    suspend fun collectArticle(articleId: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.collectArticle(articleId).await() }
    }

    suspend fun unCollectArticle(articleId: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.cancelCollectArticle(articleId).await() }
    }

    suspend fun getLastedProject(page:Int):WanResponse<ArticleList>{
        return apiCall { WanRetrofitClient.service.getLastedProject(page).await() }
    }
}
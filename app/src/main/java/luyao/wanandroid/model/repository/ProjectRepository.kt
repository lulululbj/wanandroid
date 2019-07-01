package luyao.wanandroid.model.repository

import luyao.wanandroid.model.api.BaseRepository
import luyao.wanandroid.model.api.WanRetrofitClient
import luyao.wanandroid.model.bean.ArticleList
import luyao.wanandroid.model.bean.SystemParent
import luyao.wanandroid.model.bean.WanResponse

/**
 * Created by luyao
 * on 2019/4/10 14:18
 */
class ProjectRepository : BaseRepository() {

    suspend fun getProjectTypeDetailList(page: Int, cid: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.getProjectTypeDetail(page, cid) }
    }

    suspend fun getProjectTypeList(): WanResponse<List<SystemParent>> {
        return apiCall { WanRetrofitClient.service.getProjectType() }
    }

    suspend fun collectArticle(articleId: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.collectArticle(articleId) }
    }

    suspend fun unCollectArticle(articleId: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.cancelCollectArticle(articleId) }
    }

    suspend fun getLastedProject(page: Int): WanResponse<ArticleList> {
        return apiCall { WanRetrofitClient.service.getLastedProject(page) }
    }

    suspend fun getBlog(): WanResponse<List<SystemParent>> {
        return apiCall { WanRetrofitClient.service.getBlogType() }
    }


}
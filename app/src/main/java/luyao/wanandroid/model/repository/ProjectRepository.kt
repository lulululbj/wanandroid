package luyao.wanandroid.model.repository

import luyao.wanandroid.core.Result
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

    suspend fun getProjectTypeDetailList(page: Int, cid: Int): Result<ArticleList> {
        return safeApiCall(call = {requestProjectTypeDetailList(page, cid)},errorMessage = "发生未知错误")
    }

    private suspend fun requestProjectTypeDetailList(page: Int, cid: Int) =
            executeResponse(WanRetrofitClient.service.getProjectTypeDetail(page, cid))

    suspend fun getProjectTypeList(): WanResponse<List<SystemParent>> {
        return apiCall { WanRetrofitClient.service.getProjectType() }
    }

    suspend fun getLastedProject(page: Int): Result<ArticleList> {
        return safeApiCall(call = {requestLastedProject(page)},errorMessage = "发生未知错误")
    }

    private suspend fun requestLastedProject(page: Int):Result<ArticleList> =
            executeResponse(WanRetrofitClient.service.getLastedProject(page))

    suspend fun getBlog(): WanResponse<List<SystemParent>> {
        return apiCall { WanRetrofitClient.service.getBlogType() }
    }


}
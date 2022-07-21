package luyao.wanandroid.model.repository

import luyao.wanandroid.model.api.BaseRepository
import luyao.wanandroid.model.api.WanRetrofitClient
import luyao.wanandroid.model.bean.ArticleList
import luyao.wanandroid.model.bean.SystemParent
import luyao.wanandroid.model.bean.Result
/**
 * Created by luyao
 * on 2019/4/10 14:18
 */
object ProjectRepository : BaseRepository() {

    suspend fun getProjectTypeDetailList(page: Int, cid: Int): Result<ArticleList> {
        return safeApiCall(call = {requestProjectTypeDetailList(page, cid)},errorMessage = "发生未知错误")
    }

    suspend fun getLastedProject(page: Int): Result<ArticleList> {
        return safeApiCall(call = {requestLastedProject(page)},errorMessage = "发生未知错误")
    }

    suspend fun getProjectTypeList(): Result<List<SystemParent>> {
        return safeApiCall(call = {requestProjectTypeList()},errorMessage = "网络错误")
    }

    suspend fun getBlog(): Result<List<SystemParent>> {
        return safeApiCall(call = {requestBlogTypeList()},errorMessage = "网络错误")
    }

    private suspend fun requestProjectTypeDetailList(page: Int, cid: Int) =
            executeResponse(WanRetrofitClient.service.getProjectTypeDetail(page, cid))

    private suspend fun requestLastedProject(page: Int): Result<ArticleList> =
            executeResponse(WanRetrofitClient.service.getLastedProject(page))

    private suspend fun requestProjectTypeList() =
            executeResponse(WanRetrofitClient.service.getProjectType())

    private suspend fun requestBlogTypeList() =
            executeResponse(WanRetrofitClient.service.getBlogType())

}
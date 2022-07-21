package luyao.wanandroid.model.repository

import luyao.wanandroid.model.bean.Result
import luyao.wanandroid.model.api.BaseRepository
import luyao.wanandroid.model.api.WanRetrofitClient
import luyao.wanandroid.model.bean.ArticleList
import luyao.wanandroid.model.bean.SystemParent

/**
 * Created by luyao
 * on 2019/4/10 14:34
 */
object SystemRepository : BaseRepository() {

    suspend fun getSystemTypeDetail(cid: Int, page: Int): Result<ArticleList> {
        return safeApiCall(call = { requestSystemTypeDetail(cid, page) }, errorMessage = "网络错误")
    }

    suspend fun getSystemTypes(): Result<List<SystemParent>> {
        return safeApiCall(call = { requestSystemTypes() }, errorMessage = "网络错误")
    }

    suspend fun getBlogArticle(cid: Int, page: Int): Result<ArticleList> {
        return safeApiCall(call = { requestBlogArticle(cid, page) }, errorMessage = "网络错误")
    }

    private suspend fun requestSystemTypes(): Result<List<SystemParent>> =
        executeResponse(WanRetrofitClient.service.getSystemType())

    private suspend fun requestSystemTypeDetail(cid: Int, page: Int): Result<ArticleList> =
        executeResponse(WanRetrofitClient.service.getSystemTypeDetail(page, cid))

    private suspend fun requestBlogArticle(cid: Int, page: Int): Result<ArticleList> =
        executeResponse(WanRetrofitClient.service.getSystemTypeDetail(page, cid))
}
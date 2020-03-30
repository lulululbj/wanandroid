package luyao.wanandroid.model.repository

import luyao.mvvm.core.Result
import luyao.wanandroid.model.api.BaseRepository
import luyao.wanandroid.model.api.WanRetrofitClient

/**
 * Created by luyao
 * on 2019/10/15 16:31
 */
class ShareRepository : BaseRepository() {


    suspend fun shareArticle(title: String, url: String): Result<String> {
        return safeApiCall(call = { requestShareArticle(title, url) }, errorMessage = "分享失败")
    }


    private suspend fun requestShareArticle(title: String, url: String): Result<String> =
            executeResponse(WanRetrofitClient.service.shareArticle(title, url))
}
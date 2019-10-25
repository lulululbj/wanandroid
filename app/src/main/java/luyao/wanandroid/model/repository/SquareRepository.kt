package luyao.wanandroid.model.repository

import luyao.wanandroid.util.isSuccess
import luyao.wanandroid.core.Result
import luyao.wanandroid.model.api.BaseRepository
import luyao.wanandroid.model.api.WanRetrofitClient
import luyao.wanandroid.model.bean.ArticleList
import java.io.IOException

/**
 * Created by luyao
 * on 2019/10/15 10:33
 */
class SquareRepository :  BaseRepository(){


    suspend fun getSquareArticleList(page:Int):Result<ArticleList>{
        return safeApiCall(call = {requestSquareArticleList(page)},errorMessage = "网络异常")
    }

    private suspend fun requestSquareArticleList(page: Int):Result<ArticleList>{
        val response = WanRetrofitClient.service.getSquareArticleList(page)
        return if (response.isSuccess()) Result.Success(response.data)
        else Result.Error(IOException(response.errorMsg))

    }
}
package luyao.wanandroid.api

import io.reactivex.Observable
import luyao.wanandroid.bean.ArticleList
import luyao.wanandroid.bean.Banner
import luyao.wanandroid.bean.WanResponse
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Created by luyao
 * on 2018/3/13 14:33
 */
interface WanService {

    companion object {
        const val BASE_URL = "http://www.wanandroid.com"
    }

    @GET("/article/list/{page}/json")
    fun getHomeArticles(@Path("page") page: Int): Observable<WanResponse<ArticleList>>

    @GET("/banner/json")
    fun getBanner(): Observable<WanResponse<List<Banner>>>
}
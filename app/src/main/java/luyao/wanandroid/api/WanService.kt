package luyao.wanandroid.api

import io.reactivex.Observable
import luyao.wanandroid.bean.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


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

    @GET("/tree/json")
    fun getSystemType(): Observable<WanResponse<List<SystemParent>>>

    @GET("/article/list/{page}/json")
    fun getSystemTypeDetail(@Path("page") page: Int, @Query("cid") cid: Int): Observable<WanResponse<ArticleList>>

    @GET("/navi/json")
    fun getNavigation(): Observable<WanResponse<List<Navigation>>>

    @GET("/project/tree/json")
    fun getProjectType(): Observable<WanResponse<List<SystemParent>>>

    @GET("/project/list/{page}/json")
    fun getProjectTypeDetail(@Path("page") page: Int, @Query("cid") cid: Int): Observable<WanResponse<ArticleList>>
}
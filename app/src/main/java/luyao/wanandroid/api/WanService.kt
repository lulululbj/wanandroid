package luyao.wanandroid.api

import kotlinx.coroutines.experimental.Deferred
import luyao.wanandroid.bean.*
import retrofit2.http.*


/**
 * Created by luyao
 * on 2018/3/13 14:33
 */
interface WanService {

    companion object {
        const val BASE_URL = "http://www.wanandroid.com"
    }

    @GET("/article/list/{page}/json")
    fun getHomeArticles(@Path("page") page: Int): Deferred<WanResponse<ArticleList>>

    @GET("/banner/json")
    fun getBanner(): Deferred<WanResponse<List<Banner>>>

    @GET("/tree/json")
    fun getSystemType(): Deferred<WanResponse<List<SystemParent>>>

    @GET("/article/list/{page}/json")
    fun getSystemTypeDetail(@Path("page") page: Int, @Query("cid") cid: Int): Deferred<WanResponse<ArticleList>>

    @GET("/navi/json")
    fun getNavigation(): Deferred<WanResponse<List<Navigation>>>

    @GET("/project/tree/json")
    fun getProjectType(): Deferred<WanResponse<List<SystemParent>>>

    @GET("/project/list/{page}/json")
    fun getProjectTypeDetail(@Path("page") page: Int, @Query("cid") cid: Int): Deferred<WanResponse<ArticleList>>

    @GET("/friend/json")
    fun getWebsites(): Deferred<WanResponse<List<Hot>>>

    @GET("/hotkey/json")
    fun getHot(): Deferred<WanResponse<List<Hot>>>

    @FormUrlEncoded
    @POST("/article/query/{page}/json")
    fun searchHot(@Path("page") page: Int, @Field("k") key: String): Deferred<WanResponse<ArticleList>>

    @FormUrlEncoded
    @POST("/user/login")
    fun login(@Field("username") userName: String, @Field("password") passWord: String): Deferred<WanResponse<User>>

    @FormUrlEncoded
    @POST("/user/register")
    fun register(@Field("username") userName: String, @Field("password") passWord: String, @Field("repassword") rePassWord: String): Deferred<WanResponse<User>>

    @GET("/lg/collect/list/{page}/json")
    fun getCollectArticles(@Path("page") page: Int): Deferred<WanResponse<ArticleList>>

    @POST("/lg/collect/{id}/json")
    fun collectArticle(@Path("id") id: Int): Deferred<WanResponse<ArticleList>>

    @POST("/lg/uncollect_originId/{id}/json")
    fun cancelCollectArticle(@Path("id") id: Int): Deferred<WanResponse<ArticleList>>
}
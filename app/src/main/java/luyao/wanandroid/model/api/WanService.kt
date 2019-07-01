package luyao.wanandroid.model.api

import luyao.wanandroid.model.bean.*
import retrofit2.http.*


/**
 * Created by luyao
 * on 2018/3/13 14:33
 */
interface WanService {

    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }

    @GET("/article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): WanResponse<ArticleList>

    @GET("/banner/json")
    suspend fun getBanner(): WanResponse<List<Banner>>

    @GET("/tree/json")
    suspend fun getSystemType(): WanResponse<List<SystemParent>>

    @GET("/article/list/{page}/json")
    suspend fun getSystemTypeDetail(@Path("page") page: Int, @Query("cid") cid: Int): WanResponse<ArticleList>

    @GET("/navi/json")
    suspend fun getNavigation(): WanResponse<List<Navigation>>

    @GET("/project/tree/json")
    suspend fun getProjectType(): WanResponse<List<SystemParent>>

    @GET("/wxarticle/chapters/json")
    suspend fun getBlogType(): WanResponse<List<SystemParent>>

    @GET("/wxarticle/list/{id}/{page}/json")
    fun getBlogArticle(@Path("id") id: Int, @Path("page") page: Int): WanResponse<ArticleList>

    @GET("/project/list/{page}/json")
    suspend fun getProjectTypeDetail(@Path("page") page: Int, @Query("cid") cid: Int): WanResponse<ArticleList>

    @GET("/article/listproject/{page}/json")
    suspend fun getLastedProject(@Path("page") page: Int): WanResponse<ArticleList>

    @GET("/friend/json")
    suspend fun getWebsites(): WanResponse<List<Hot>>

    @GET("/hotkey/json")
    suspend fun getHot(): WanResponse<List<Hot>>

    @FormUrlEncoded
    @POST("/article/query/{page}/json")
    suspend fun searchHot(@Path("page") page: Int, @Field("k") key: String): WanResponse<ArticleList>

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(@Field("username") userName: String, @Field("password") passWord: String): WanResponse<User>

    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(@Field("username") userName: String, @Field("password") passWord: String, @Field("repassword") rePassWord: String): WanResponse<User>

    @GET("/lg/collect/list/{page}/json")
    suspend fun getCollectArticles(@Path("page") page: Int): WanResponse<ArticleList>

    @POST("/lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id") id: Int): WanResponse<ArticleList>

    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun cancelCollectArticle(@Path("id") id: Int): WanResponse<ArticleList>


}
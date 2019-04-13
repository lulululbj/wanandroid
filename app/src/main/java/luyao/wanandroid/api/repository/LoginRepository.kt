package luyao.wanandroid.api.repository

import luyao.base.BaseRepository
import luyao.base.WanResponse
import luyao.wanandroid.api.WanRetrofitClient
import luyao.wanandroid.model.bean.User

/**
 * Created by luyao
 * on 2019/4/10 9:42
 */
class LoginRepository : BaseRepository() {

    suspend fun login(userName: String, passWord: String): WanResponse<User> {
        return apiCall { WanRetrofitClient.service.login(userName, passWord).await() }
    }

    suspend fun register(userName: String, passWord: String): WanResponse<User> {
        return apiCall { WanRetrofitClient.service.register(userName, passWord, passWord).await() }
    }


}
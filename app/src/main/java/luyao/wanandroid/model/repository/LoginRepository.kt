package luyao.wanandroid.model.repository

import com.google.gson.Gson
import luyao.wanandroid.App
import luyao.wanandroid.core.Result
import luyao.wanandroid.model.api.BaseRepository
import luyao.wanandroid.model.api.WanRetrofitClient
import luyao.wanandroid.model.bean.User
import luyao.wanandroid.model.bean.WanResponse
import luyao.wanandroid.util.Preference
import java.io.IOException

/**
 * Created by luyao
 * on 2019/4/10 9:42
 */
class LoginRepository : BaseRepository() {

    private var isLogin by Preference(Preference.IS_LOGIN, false)
    private var userJson by Preference(Preference.USER_GSON, "")


    suspend fun login(userName: String, passWord: String): Result<User> {
//        return apiCall { WanRetrofitClient.service.login(userName, passWord) }
        return safeApiCall(call = { requestLogin(userName, passWord) },
                errorMessage = "登录失败!")
    }

    private suspend fun requestLogin(userName: String, passWord: String): Result<User> {
        val response = WanRetrofitClient.service.login(userName, passWord)
        return if (response.errorCode != -1) {
            val user = response.data
            isLogin = true
            userJson = Gson().toJson(user)
            App.CURRENT_USER = user
            Result.Success(user)
        } else {
            Result.Error(IOException(response.errorMsg))
        }
    }

    suspend fun register(userName: String, passWord: String): WanResponse<User> {
        return apiCall { WanRetrofitClient.service.register(userName, passWord, passWord) }
    }

}
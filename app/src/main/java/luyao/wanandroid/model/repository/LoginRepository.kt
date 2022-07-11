package luyao.wanandroid.model.repository

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import luyao.wanandroid.App
import luyao.wanandroid.model.api.BaseRepository
import luyao.wanandroid.model.api.WanService
import luyao.wanandroid.model.bean.User
import luyao.wanandroid.model.bean.doError
import luyao.wanandroid.model.bean.doSuccess
import luyao.wanandroid.ui.login.LoginUiState
import luyao.wanandroid.util.Preference

/**
 * Created by luyao
 * on 2019/4/10 9:42
 */
class LoginRepository(val service: WanService) : BaseRepository() {

    private var isLogin by Preference(Preference.IS_LOGIN, false)
    private var userJson by Preference(Preference.USER_GSON, "")

    suspend fun loginFlow(userName: String, passWord: String) = flow {

        // 输入不能为空
        if (userName.isBlank() || passWord.isBlank()) {
            emit(LoginUiState(enableLoginButton = false))
            return@flow
        }

        service.login(userName, passWord).doSuccess { user ->
            isLogin = true
            userJson = Gson().toJson(user)
            App.CURRENT_USER = user
            emit(LoginUiState(isSuccess = user, enableLoginButton = true))
        }.doError { errorMsg ->
            emit(LoginUiState<User>(isError = errorMsg, enableLoginButton = true))
        }
    }.onStart {
        emit(LoginUiState(isLoading = true))
    }.flowOn(Dispatchers.IO)
            .catch { emit(LoginUiState(isError = it.message, enableLoginButton = true)) }


    suspend fun registerFlow(userName: String, passWord: String) = flow<LoginUiState<User>> {

        // 输入不能为空
        if (userName.isBlank() || passWord.isBlank()) {
            emit(LoginUiState(enableLoginButton = false))
            return@flow
        }

        service.register(userName, passWord, passWord).doSuccess {
            loginFlow(userName,passWord)
        }.doError { errorMsg ->
            emit(LoginUiState(isError = errorMsg, enableLoginButton = true))
        }
    }.onStart {
        emit(LoginUiState(isLoading = true))
    }.flowOn(Dispatchers.IO)
            .catch { emit(LoginUiState(isError = it.message, enableLoginButton = true)) }

}
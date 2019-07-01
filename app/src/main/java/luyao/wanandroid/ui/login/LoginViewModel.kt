package luyao.wanandroid.ui.login

import androidx.lifecycle.MutableLiveData
import executeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import luyao.util.ktx.base.BaseViewModel
import luyao.wanandroid.model.repository.LoginRepository
import luyao.wanandroid.model.bean.User

/**
 * Created by luyao
 * on 2019/4/2 16:36
 */
class LoginViewModel : BaseViewModel() {

    val mLoginUser: MutableLiveData<User> = MutableLiveData()
    val mRegisterUser: MutableLiveData<User> = MutableLiveData()
    val errMsg: MutableLiveData<String> = MutableLiveData()
    private val repository by lazy { LoginRepository() }

    fun login(userName: String, passWord: String) {
        launch {
            val response = withContext(Dispatchers.IO) { repository.login(userName, passWord) }
            executeResponse(response, { mLoginUser.value = response.data }, { errMsg.value = response.errorMsg })
        }
    }

    fun register(userName: String, passWord: String) {
        launch {
            val result = withContext(Dispatchers.IO) { repository.register(userName, passWord) }
            executeResponse(result, { mRegisterUser.value = result.data }, { errMsg.value = result.errorMsg })
        }
    }


}
package luyao.wanandroid.ui.login

import android.arch.lifecycle.MutableLiveData
import luyao.base.BaseViewModel
import luyao.wanandroid.api.WanRetrofitClient
import luyao.wanandroid.bean.User
import luyao.wanandroid.ext.launchOnUITryCatch

/**
 * Created by luyao
 * on 2019/4/2 16:36
 */
class LoginViewModel : BaseViewModel() {

    val mLoginUser: MutableLiveData<User> = MutableLiveData()
    val mRegisterUser: MutableLiveData<User> = MutableLiveData()
    val errMsg: MutableLiveData<String> = MutableLiveData()

    fun login(userName: String, passWord: String) {
        launchOnUITryCatch({
            val result = WanRetrofitClient.service.login(userName, passWord).await()
            with(result) {
                if (errorCode == -1) errMsg.value = errorMsg
                else mLoginUser.value = result.data
            }
        }, {}, {}, true)
    }

    fun register(userName: String, passWord: String) {
        launchOnUITryCatch({
            val result = WanRetrofitClient.service.register(userName, passWord, passWord).await()
            with(result) {
                if (errorCode == -1) errMsg.value = errorMsg
                else mRegisterUser.value = result.data
            }
        }, {}, {}, true)
    }
}
package luyao.wanandroid.ui.login

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import luyao.wanandroid.api.WanRetrofitClient

/**
 * Created by Lu
 * on 2018/4/5 08:13
 */
class LoginPresenter(private val mView: LoginContract.View) : LoginContract.Presenter {

    init {
        mView.mPresenter = this
    }

    override fun start() {

    }

    override fun register(userName: String, passWord: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = WanRetrofitClient.service.register(userName, passWord, passWord).await()
            with(result) {
                if (errorCode == -1) mView.registerError(errorMsg) else mView.register(data)
            }
        }
    }

    override fun login(userName: String, passWord: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = WanRetrofitClient.service.login(userName, passWord).await()
            with(result) {
                if (errorCode == -1)  mView.loginError(errorMsg) else mView.login(data)
            }
        }
    }
}
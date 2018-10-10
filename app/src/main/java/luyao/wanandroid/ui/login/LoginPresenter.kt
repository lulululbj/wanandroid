package luyao.wanandroid.ui.login

import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import luyao.wanandroid.api.WanRetrofitClient
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by Lu
 * on 2018/4/5 08:13
 */
class LoginPresenter(
        private val mView: LoginContract.View,
        private val uiContext: CoroutineContext = UI
) : LoginContract.Presenter {

    init {
        mView.mPresenter = this
    }

    override fun start() {

    }

    override fun register(userName: String, passWord: String) {
        launch(uiContext) {
            val result = WanRetrofitClient.service.register(userName, passWord, passWord).await()
            with(result) {
                if (errorCode == -1) mView.registerError(errorMsg) else mView.register(data)
            }
        }
    }

    override fun login(userName: String, passWord: String) {
        launch(uiContext) {
            val result = WanRetrofitClient.service.login(userName, passWord).await()
            with(result) {
                if (errorCode == -1)  mView.loginError(errorMsg) else mView.login(data)
            }
        }
    }
}
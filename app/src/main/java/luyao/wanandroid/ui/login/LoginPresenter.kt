package luyao.wanandroid.ui.login

import luyao.gayhub.base.mvp.BasePresenter
import luyao.gayhub.base.rx.UIScheduler
import luyao.wanandroid.api.WanRetrofitClient

/**
 * Created by Lu
 * on 2018/4/5 08:13
 */
class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    override fun register(userName: String, passWord: String) {
        val d = WanRetrofitClient.service
                .register(userName, passWord, passWord)
                .compose(UIScheduler())
                .subscribe({
                    if (it.errorCode == -1)
                        getView()?.showError(it.errorMsg)
                    else
                        getView()?.register(it.data)
                }, { error -> getView()?.showError(error.message) })
        addSubscription(d)
    }

    override fun login(userName: String, passWord: String) {
        val d = WanRetrofitClient.service
                .login(userName, passWord)
                .compose(UIScheduler())
                .subscribe({
                    if (it.errorCode == -1)
                        getView()?.showError(it.errorMsg)
                    else
                        getView()?.login(it.data)
                }, { error -> getView()?.showError(error.message) })
        addSubscription(d)
    }
}
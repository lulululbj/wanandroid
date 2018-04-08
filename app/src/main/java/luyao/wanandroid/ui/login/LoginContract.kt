package luyao.wanandroid.ui.login

import luyao.gayhub.base.mvp.IPresenter
import luyao.gayhub.base.mvp.IView
import luyao.wanandroid.bean.User

/**
 * Created by Lu
 * on 2018/4/5 07:57
 */
interface LoginContract {

    interface View : IView {
        fun login(user: User)
        fun register(user: User)
    }

    interface Presenter : IPresenter<View> {
        fun login(userName: String, passWord: String)
        fun register(userName: String, passWord: String)
    }
}
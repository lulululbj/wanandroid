package luyao.wanandroid.ui.login

import luyao.wanandroid.BasePresenter
import luyao.wanandroid.BaseView
import luyao.wanandroid.bean.User

/**
 * Created by Lu
 * on 2018/4/5 07:57
 */
interface LoginContract {

    interface View : BaseView<Presenter> {
        fun login(user: User)
        fun register(user: User)
        fun registerError(message:String)
        fun loginError(message:String)
    }

    interface Presenter : BasePresenter {
        fun login(userName: String, passWord: String)
        fun register(userName: String, passWord: String)
    }
}
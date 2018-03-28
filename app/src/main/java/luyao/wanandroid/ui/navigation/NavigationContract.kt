package luyao.wanandroid.ui.navigation

import luyao.gayhub.base.mvp.IPresenter
import luyao.gayhub.base.mvp.IView
import luyao.wanandroid.bean.Navigation

/**
 * Created by Lu
 * on 2018/3/28 21:26
 */
interface NavigationContract {

    interface View : IView {
        fun getNavigation(navigationList: List<Navigation>)
    }

    interface Presenter : IPresenter<View> {
        fun getNavigation()
    }
}
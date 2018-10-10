package luyao.wanandroid.ui.navigation

import luyao.wanandroid.BasePresenter
import luyao.wanandroid.BaseView
import luyao.wanandroid.bean.Navigation

/**
 * Created by Lu
 * on 2018/3/28 21:26
 */
interface NavigationContract {

    interface View : BaseView<Presenter> {
        fun getNavigation(navigationList: List<Navigation>)
    }

    interface Presenter : BasePresenter {
        fun getNavigation()
    }
}
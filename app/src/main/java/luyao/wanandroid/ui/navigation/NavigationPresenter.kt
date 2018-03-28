package luyao.wanandroid.ui.navigation

import luyao.gayhub.base.mvp.BasePresenter
import luyao.gayhub.base.rx.UIScheduler
import luyao.wanandroid.api.WanRetrofitClient

/**
 * Created by Lu
 * on 2018/3/28 21:27
 */
class NavigationPresenter : BasePresenter<NavigationContract.View>(), NavigationContract.Presenter {
    override fun getNavigation() {
        val d = WanRetrofitClient.service
                .getNavigation()
                .compose(UIScheduler())
                .subscribe { getView()?.getNavigation(it.data) }
        addSubscription(d)
    }
}
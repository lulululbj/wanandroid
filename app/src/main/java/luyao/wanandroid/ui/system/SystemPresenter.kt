package luyao.wanandroid.ui.system

import luyao.gayhub.base.mvp.BasePresenter
import luyao.gayhub.base.rx.UIScheduler
import luyao.wanandroid.api.WanRetrofitClient

/**
 * Created by Lu
 * on 2018/3/26 21:39
 */
class SystemPresenter : BasePresenter<SystemContract.View>(), SystemContract.Presenter {

    override fun getSystemTypes() {
        val d = WanRetrofitClient.service
                .getSystemType()
                .compose(UIScheduler())
                .subscribe({ getView()?.getSystemTypes(it.data) })
        addSubscription(d)
    }
}
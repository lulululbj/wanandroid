package luyao.wanandroid.ui.search

import luyao.gayhub.base.mvp.BasePresenter
import luyao.gayhub.base.rx.UIScheduler
import luyao.wanandroid.api.WanRetrofitClient

/**
 * Created by Lu
 * on 2018/4/2 21:57
 */
class SearchPresenter : BasePresenter<SearchContract.View>(), SearchContract.Presenter {
    override fun searchHot(page: Int, key: String) {
        val d = WanRetrofitClient.service
                .searchHot(page, key)
                .compose(UIScheduler())
                .subscribe({ getView()?.searchHot(it.data) })
        addSubscription(d)
    }

    override fun getWebsites() {
        val d = WanRetrofitClient.service
                .getWebsites()
                .compose(UIScheduler())
                .subscribe({ getView()?.getWebsites(it.data) })
        addSubscription(d)
    }

    override fun getHotSearch() {
        val d = WanRetrofitClient.service
                .getHot()
                .compose(UIScheduler())
                .subscribe({ getView()?.getHotSearch(it.data) })
        addSubscription(d)

    }
}
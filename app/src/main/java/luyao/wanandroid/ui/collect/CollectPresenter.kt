package luyao.wanandroid.ui.collect

import luyao.gayhub.base.mvp.BasePresenter
import luyao.gayhub.base.rx.UIScheduler
import luyao.wanandroid.api.WanRetrofitClient

/**
 * Created by Lu
 * on 2018/4/10 22:12
 */
class CollectPresenter : BasePresenter<CollectContract.View>(), CollectContract.Presenter {
    override fun getCollectArticles(page: Int) {
        val d = WanRetrofitClient.service
                .getCollectArticles(page)
                .compose(UIScheduler())
                .subscribe({
                    if (it.errorCode == -1)
                        getView()?.showError(it.errorMsg)
                    else
                        getView()?.getCollectArticles(it.data)
                })
        addSubscription(d)
    }
}
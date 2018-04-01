package luyao.wanandroid.ui.project

import luyao.gayhub.base.mvp.BasePresenter
import luyao.gayhub.base.rx.UIScheduler
import luyao.wanandroid.api.WanRetrofitClient

/**
 * Created by Lu
 * on 2018/4/1 16:46
 */
class ProjectPresenter : BasePresenter<ProjectContract.View>(), ProjectContract.Presenter {
    override fun getProjectTypeDetailList(page: Int, cid: Int) {
        val d=WanRetrofitClient.service
                .getProjectTypeDetail(page, cid)
                .compose(UIScheduler())
                .subscribe({getView()?.getProjectTypeDetailList(it.data)})
        addSubscription(d)
    }

    override fun getProjectTypeList() {
        val d = WanRetrofitClient.service
                .getProjectType()
                .compose(UIScheduler())
                .subscribe { getView()?.getProjectTypeList(it.data) }
        addSubscription(d)
    }
}
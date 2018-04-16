package luyao.wanandroid.ui.home

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import luyao.gayhub.base.mvp.BasePresenter
import luyao.gayhub.base.rx.UIScheduler
import luyao.wanandroid.api.WanRetrofitClient
import luyao.wanandroid.bean.Article

/**
 * Created by luyao
 * on 2018/3/13 15:05
 */
class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {

    override fun getBanners() {
        val d = WanRetrofitClient.service.getBanner()
                .compose(UIScheduler())
                .subscribe { getView()?.getBanner(it.data) }
        addSubscription(d)
    }

    override fun getArticles(page: Int) {
        val d: Disposable = WanRetrofitClient.service.getHomeArticles(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getView()?.getArticles(it.data)
                }, {

                })
        addSubscription(d)
    }

    override fun collectArticle(article: Article) {
        val d = WanRetrofitClient.service
                .collectArticle(article.id)
                .compose(UIScheduler())
                .subscribe({ getView()?.collectArticle(article) })
        addSubscription(d)
    }

    override fun cancelCollectArticle(article: Article) {
        val d = WanRetrofitClient.service
                .cancelCollectArticle(article.id)
                .compose(UIScheduler())
                .subscribe({ getView()?.cancleCollectArticle(article) })
        addSubscription(d)
    }
}
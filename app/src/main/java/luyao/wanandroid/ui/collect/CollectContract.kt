package luyao.wanandroid.ui.collect

import luyao.gayhub.base.mvp.IPresenter
import luyao.gayhub.base.mvp.IView
import luyao.wanandroid.bean.ArticleList

/**
 * Created by Lu
 * on 2018/4/10 22:10
 */
class CollectContract {

    interface View : IView {
        fun getCollectArticles(articleList: ArticleList)
    }

    interface Presenter : IPresenter<View> {
        fun getCollectArticles(page: Int)
    }
}
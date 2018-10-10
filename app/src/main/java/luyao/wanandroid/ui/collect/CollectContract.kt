package luyao.wanandroid.ui.collect

import luyao.wanandroid.BasePresenter
import luyao.wanandroid.BaseView
import luyao.wanandroid.bean.ArticleList

/**
 * Created by Lu
 * on 2018/4/10 22:10
 */
class CollectContract {

    interface View : BaseView<Presenter> {
        fun getCollectArticles(articleList: ArticleList)
        fun getCollectArticlesError(message: String)
    }

    interface Presenter : BasePresenter {
        fun getCollectArticles(page: Int)
    }
}
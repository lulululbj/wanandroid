package luyao.wanandroid.ui.home

import luyao.wanandroid.BasePresenter
import luyao.wanandroid.BaseView
import luyao.wanandroid.bean.Article
import luyao.wanandroid.bean.ArticleList
import luyao.wanandroid.bean.Banner

/**
 * Created by luyao
 * on 2018/3/13 14:20
 */
interface HomeContract {

    interface View : BaseView<Presenter> {
        fun getArticles(articleList: ArticleList)
        fun getBanner(bannerList: List<Banner>)
        fun collectArticle(article: Article)
        fun cancleCollectArticle(article: Article)
    }

    interface Presenter : BasePresenter {
        fun getArticles(page: Int)
        fun getBanners()
        fun collectArticle(article: Article)
        fun cancelCollectArticle(article: Article)
    }
}
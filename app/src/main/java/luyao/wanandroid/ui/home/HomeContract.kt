package luyao.wanandroid.ui.home

import luyao.gayhub.base.mvp.IPresenter
import luyao.gayhub.base.mvp.IView
import luyao.wanandroid.bean.Article
import luyao.wanandroid.bean.ArticleList
import luyao.wanandroid.bean.Banner

/**
 * Created by luyao
 * on 2018/3/13 14:20
 */
interface HomeContract {

    interface View : IView {
        fun getArticles(articleList: ArticleList)
        fun getBanner(bannerList: List<Banner>)
        fun collectArticle(article: Article)
        fun cancleCollectArticle(article: Article)
    }

    interface Presenter : IPresenter<View> {
        fun getArticles(page: Int)
        fun getBanners()
        fun collectArticle(article: Article)
        fun cancelCollectArticle(article: Article)
    }
}
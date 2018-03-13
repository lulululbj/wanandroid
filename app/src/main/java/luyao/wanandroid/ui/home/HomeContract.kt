package luyao.wanandroid.ui.home

import luyao.gayhub.base.mvp.IPresenter
import luyao.gayhub.base.mvp.IView
import luyao.wanandroid.bean.ArticleList

/**
 * Created by luyao
 * on 2018/3/13 14:20
 */
interface HomeContract {

    interface View : IView{
        fun getArticles(articleList: ArticleList)
    }

    interface Presenter : IPresenter<View>{
        fun getArticles(page:Int)
    }
}
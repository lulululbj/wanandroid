package luyao.wanandroid.ui.search

import luyao.gayhub.base.mvp.IPresenter
import luyao.gayhub.base.mvp.IView
import luyao.wanandroid.bean.ArticleList
import luyao.wanandroid.bean.Hot

/**
 * Created by Lu
 * on 2018/4/2 21:45
 */
interface SearchContract {

    interface View : IView {
        fun getWebsites(webSites: List<Hot>)
        fun getHotSearch(hotWords: List<Hot>)
        fun searchHot(articleList: ArticleList)
    }

    interface Presenter : IPresenter<View> {
        fun getWebsites()
        fun getHotSearch()
        fun searchHot(page:Int,key:String)
    }
}
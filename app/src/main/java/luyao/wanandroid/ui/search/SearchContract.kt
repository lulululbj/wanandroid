package luyao.wanandroid.ui.search

import luyao.wanandroid.BasePresenter
import luyao.wanandroid.BaseView
import luyao.wanandroid.bean.ArticleList
import luyao.wanandroid.bean.Hot

/**
 * Created by Lu
 * on 2018/4/2 21:45
 */
interface SearchContract {

    interface View : BaseView<Presenter> {
        fun getWebsites(webSites: List<Hot>)
        fun getHotSearch(hotWords: List<Hot>)
        fun searchHot(articleList: ArticleList)
    }

    interface Presenter : BasePresenter {
        fun getWebsites()
        fun getHotSearch()
        fun searchHot(page:Int,key:String)
    }
}
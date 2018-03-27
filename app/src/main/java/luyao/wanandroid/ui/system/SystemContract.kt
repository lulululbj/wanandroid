package luyao.wanandroid.ui.system

import luyao.gayhub.base.mvp.IPresenter
import luyao.gayhub.base.mvp.IView
import luyao.wanandroid.bean.ArticleList
import luyao.wanandroid.bean.SystemParent

/**
 * Created by Lu
 * on 2018/3/26 21:31
 */
interface SystemContract {

    interface View : IView {
        fun getSystemTypes(systemList: List<SystemParent>)
        fun getSystemTypeDetail(articleList: ArticleList)
    }

    interface Presenter : IPresenter<View> {
        fun getSystemTypes()
        fun getSystemTypeDetail(id: Int, page: Int)
    }
}
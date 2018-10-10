package luyao.wanandroid.ui.system

import luyao.wanandroid.BasePresenter
import luyao.wanandroid.BaseView
import luyao.wanandroid.bean.ArticleList
import luyao.wanandroid.bean.SystemParent

/**
 * Created by Lu
 * on 2018/3/26 21:31
 */
interface SystemContract {

    interface View : BaseView<Presenter> {
        fun getSystemTypes(systemList: List<SystemParent>)
        fun getSystemTypeDetail(articleList: ArticleList)
    }

    interface Presenter : BasePresenter {
        fun getSystemTypes()
        fun getSystemTypeDetail(id: Int, page: Int)
    }
}
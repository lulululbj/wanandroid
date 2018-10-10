package luyao.wanandroid.ui.project

import luyao.wanandroid.BasePresenter
import luyao.wanandroid.BaseView
import luyao.wanandroid.bean.ArticleList
import luyao.wanandroid.bean.SystemParent

/**
 * Created by Lu
 * on 2018/4/1 16:44
 */
interface ProjectContract {

    interface View : BaseView<Presenter> {
        fun getProjectTypeList(projectTypeList: List<SystemParent>)
        fun getProjectTypeDetailList(articleList: ArticleList)
    }

    interface Presenter : BasePresenter {
        fun getProjectTypeList()
        fun getProjectTypeDetailList(page: Int, cid: Int)
    }
}
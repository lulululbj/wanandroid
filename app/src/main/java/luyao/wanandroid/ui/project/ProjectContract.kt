package luyao.wanandroid.ui.project

import luyao.gayhub.base.mvp.IPresenter
import luyao.gayhub.base.mvp.IView
import luyao.wanandroid.bean.ArticleList
import luyao.wanandroid.bean.SystemParent

/**
 * Created by Lu
 * on 2018/4/1 16:44
 */
interface ProjectContract {

    interface View : IView {
        fun getProjectTypeList(projectTypeList: List<SystemParent>)
        fun getProjectTypeDetailList(articleList: ArticleList)
    }

    interface Presenter : IPresenter<View> {
        fun getProjectTypeList()
        fun getProjectTypeDetailList(page: Int, cid: Int)
    }
}
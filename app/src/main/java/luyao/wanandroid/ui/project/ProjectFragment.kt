package luyao.wanandroid.ui.project

import android.support.v4.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.fragment_project.*
import luyao.gayhub.base.BaseMvpFragment
import luyao.wanandroid.R
import luyao.wanandroid.bean.ArticleList
import luyao.wanandroid.bean.SystemParent

/**
 * Created by Lu
 * on 2018/4/1 16:52
 */
class ProjectFragment : BaseMvpFragment<ProjectContract.View, ProjectPresenter>(), ProjectContract.View {


    private val mProjectTypeList = mutableListOf<SystemParent>()
    override var mPresenter = ProjectPresenter()

    override fun getLayoutResId() = R.layout.fragment_project

    override fun initView() {
        initViewPager()
    }

    override fun initData() {
        mPresenter.getProjectTypeList()
    }

    private fun initViewPager() {
        viewPager.adapter = object : FragmentPagerAdapter(activity?.supportFragmentManager) {
            override fun getItem(position: Int) = ProjectTypeFragment.newInstance(mProjectTypeList[position].id)

            override fun getCount() = mProjectTypeList.size

            override fun getPageTitle(position: Int) = mProjectTypeList[position].name

        }
        tabLayout.setupWithViewPager(viewPager)
    }


    override fun getProjectTypeList(projectTypeList: List<SystemParent>) {
        mProjectTypeList.clear()
        mProjectTypeList.addAll(projectTypeList)
        viewPager.adapter?.notifyDataSetChanged()
    }

    override fun getProjectTypeDetailList(articleList: ArticleList) {

    }
}
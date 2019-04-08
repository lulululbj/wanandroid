package luyao.wanandroid.ui.project

import android.arch.lifecycle.Observer
import android.support.v4.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.fragment_project.*
import luyao.base.BaseFragment
import luyao.wanandroid.R
import luyao.wanandroid.bean.ArticleList
import luyao.wanandroid.bean.SystemParent

/**
 * Created by Lu
 * on 2018/4/1 16:52
 */
class ProjectFragment : BaseFragment<ProjectViewModel>() {

    override fun providerVMClass(): Class<ProjectViewModel>? = ProjectViewModel::class.java

    private val mProjectTypeList = mutableListOf<SystemParent>()

    override fun getLayoutResId() = R.layout.fragment_project

    override fun initView() {
        initViewPager()
    }

    override fun initData() {
        mViewModel.getProjectTypeList()
    }

    private fun initViewPager() {
        viewPager.adapter = object : FragmentPagerAdapter(activity?.supportFragmentManager) {
            override fun getItem(position: Int) = ProjectTypeFragment.newInstance(mProjectTypeList[position].id)

            override fun getCount() = mProjectTypeList.size

            override fun getPageTitle(position: Int) = mProjectTypeList[position].name

        }
        tabLayout.setupWithViewPager(viewPager)
    }


    private fun getProjectTypeList(projectTypeList: List<SystemParent>) {
        mProjectTypeList.clear()
        mProjectTypeList.addAll(projectTypeList)
        viewPager.adapter?.notifyDataSetChanged()
    }

    override fun startObserve() {
        mViewModel.run {
            mSystemParentList.observe(this@ProjectFragment, Observer {
                it?.run { getProjectTypeList(it) }
            })
        }
    }
}
package luyao.wanandroid.ui.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_project.*
import luyao.util.ktx.base.BaseVMFragment
import luyao.wanandroid.R
import luyao.wanandroid.model.bean.SystemParent
import luyao.wanandroid.ui.home.HomeFragment
import luyao.wanandroid.ui.system.SystemTypeFragment

/**
 * Created by Lu
 * on 2018/4/1 16:52
 */
class ProjectFragment : BaseVMFragment<ProjectViewModel>() {

    override fun providerVMClass(): Class<ProjectViewModel>? = ProjectViewModel::class.java

    private val isBlog by lazy { arguments?.getBoolean(BLOG) } // 区分是公众号还是项目分类
    private val mProjectTypeList = mutableListOf<SystemParent>()

    override fun getLayoutResId() = R.layout.fragment_project

    override fun initView() {
        initViewPager()
    }

    override fun initData() {

        isBlog?.run {
            if (this) mViewModel.getBlogType()
            else mViewModel.getProjectTypeList()
        }
    }

    companion object {

        private const val BLOG = "projectCid"
        fun newInstance(isBlog: Boolean): ProjectFragment {
            val fragment = ProjectFragment()
            val bundle = Bundle()
            bundle.putBoolean(BLOG, isBlog)
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun initViewPager() {
        viewPager.adapter = object : androidx.fragment.app.FragmentPagerAdapter(activity!!.supportFragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getItem(position: Int) = chooseFragment(position)


            override fun getCount() = mProjectTypeList.size

            override fun getPageTitle(position: Int) = mProjectTypeList[position].name

        }
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun chooseFragment(position: Int): Fragment {
        isBlog?.run {
            return if (this) SystemTypeFragment.newInstance(mProjectTypeList[position].id, true)
            else ProjectTypeFragment.newInstance(mProjectTypeList[position].id, false)
        }
        return HomeFragment()
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
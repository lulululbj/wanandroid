package luyao.wanandroid.ui.project

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_project.*
import luyao.wanandroid.R
import luyao.wanandroid.model.bean.SystemParent
import luyao.wanandroid.ui.system.SystemTypeFragment
import org.koin.androidx.viewmodel.ext.android.getViewModel

open class ProjectFragment : luyao.mvvm.core.base.BaseVMFragment<ProjectViewModel>(useDataBinding = false) {

    override fun initVM(): ProjectViewModel = getViewModel()

    private val mProjectTypeList = mutableListOf<SystemParent>()
    open var isBlog = false // 区分是公众号还是项目分类

    override fun getLayoutResId() = R.layout.activity_project

    override fun initView() {
        initViewPager()
    }

    override fun initData() {
        if (isBlog) mViewModel.getBlogType()
        else mViewModel.getProjectTypeList()
    }

    private fun initViewPager() {

        projectViewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = mProjectTypeList.size

            override fun createFragment(position: Int) = chooseFragment(position)

        }

        TabLayoutMediator(tabLayout, projectViewPager) { tab, position ->
            tab.text = mProjectTypeList[position].name
        }.attach()
    }

    private fun chooseFragment(position: Int): Fragment {
        return if (isBlog) SystemTypeFragment.newInstance(mProjectTypeList[position].id, true)
        else ProjectTypeFragment.newInstance(mProjectTypeList[position].id, false)
    }

    private fun getProjectTypeList(projectTypeList: List<SystemParent>) {
        mProjectTypeList.clear()
        mProjectTypeList.addAll(projectTypeList)
        projectViewPager.adapter?.notifyDataSetChanged()
    }

    override fun startObserve() {
        mViewModel.systemData.observe(this, Observer {
            it?.run { getProjectTypeList(it) }
        })
    }

}
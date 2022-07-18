package luyao.wanandroid.ui.project

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_project.*
import luyao.wanandroid.R
import luyao.wanandroid.databinding.ActivityProjectBinding
import luyao.wanandroid.model.bean.SystemParent
import luyao.wanandroid.ui.system.SystemTypeFragment

@AndroidEntryPoint
open class ProjectFragment :
    luyao.mvvm.core.base.BaseVMFragment<ActivityProjectBinding>(R.layout.activity_project) {

    private val projectViewModel: ProjectViewModel by viewModels()

    private val mProjectTypeList = mutableListOf<SystemParent>()
    open var isBlog = false // 区分是公众号还是项目分类


    override fun initView() {
        initViewPager()
    }

    override fun initData() {
        if (isBlog) projectViewModel.getBlogType()
        else projectViewModel.getProjectTypeList()
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
        projectViewModel.systemData.observe(viewLifecycleOwner, Observer {
            it?.run { getProjectTypeList(it) }
        })
    }

}
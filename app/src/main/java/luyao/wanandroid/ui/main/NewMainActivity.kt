package luyao.wanandroid.ui.main

import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_new_main.*
import luyao.base.BaseNormalActivity
import luyao.wanandroid.R
import luyao.wanandroid.ui.home.HomeFragment
import luyao.wanandroid.ui.navigation.NavigationFragment
import luyao.wanandroid.ui.project.ProjectTypeFragment
import luyao.wanandroid.ui.system.SystemFragment

/**
 * Created by luyao
 * on 2019/5/7 15:36
 */
class NewMainActivity : BaseNormalActivity() {

    private val titleList = arrayOf("首页", "最新项目","体系","导航")
    private val fragmentList = arrayListOf<Fragment>()
    private val homeFragment by lazy { HomeFragment() } // 首页
    private val lastedProjectFragment by lazy { ProjectTypeFragment.newInstance(0, true) } // 最新项目
    private val systemFragment by lazy { SystemFragment() } // 体系
    private val navigationFragment by lazy { NavigationFragment() } // 导航


    override fun getLayoutResId() = R.layout.activity_new_main

    init {
        fragmentList.add(homeFragment)
        fragmentList.add(lastedProjectFragment)
        fragmentList.add(systemFragment)
        fragmentList.add(navigationFragment)
    }

    override fun initView() {
        initViewPager()
    }

    override fun initData() {

    }

    private fun initViewPager() {
        viewPager.offscreenPageLimit=titleList.size
        viewPager.adapter = object : androidx.fragment.app.FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int) = fragmentList[position]

            override fun getCount() = titleList.size

            override fun getPageTitle(position: Int) = titleList[position]

        }
        tabLayout.setupWithViewPager(viewPager)
    }
}
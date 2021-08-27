package luyao.wanandroid.ui

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_home_tab.*
import luyao.mvvm.core.base.BaseFragment
import luyao.wanandroid.R
import luyao.wanandroid.ui.main.MainFragment
import luyao.wanandroid.ui.profile.ProfileFragment
import luyao.wanandroid.ui.project.BlogFragment
import luyao.wanandroid.ui.project.ProjectFragment
import luyao.wanandroid.ui.search.SearchFragment

/**
 * 这是首页 Tab
 */
class HomeTabFragment : BaseFragment() {

    override fun getLayoutResId() = R.layout.fragment_home_tab

    override fun initView() {
        initViewPager()
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelected)
    }

    override fun initData() {
    }


    private val onNavigationItemSelected = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.home -> {
                switchFragment(0)
            }
            R.id.blog -> {
                switchFragment(1)
            }
            R.id.search -> {
                switchFragment(2)
            }
            R.id.project -> {
                switchFragment(3)
            }
            R.id.profile -> {
                switchFragment(4)
            }
        }
        true
    }

    private fun switchFragment(position: Int): Boolean {
        mainViewpager.setCurrentItem(position, false)
        return true
    }

    private fun initViewPager() {
        mainViewpager.isUserInputEnabled = false
        mainViewpager.offscreenPageLimit = 2
        mainViewpager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int) = when (position) {
                0 -> MainFragment()
                1 -> BlogFragment()
                2 -> SearchFragment()
                3 -> ProjectFragment()
                4 -> ProfileFragment()
                else -> MainFragment()
            }

            override fun getItemCount() = 5
        }
    }


}

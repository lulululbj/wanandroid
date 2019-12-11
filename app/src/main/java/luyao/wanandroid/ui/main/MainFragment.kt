package luyao.wanandroid.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_new_main.*
import luyao.util.ktx.base.BaseFragment
import luyao.util.ktx.ext.startKtxActivity
import luyao.wanandroid.R
import luyao.wanandroid.ui.BrowserNormalActivity
import luyao.wanandroid.ui.about.AboutActivity
import luyao.wanandroid.ui.collect.MyCollectActivity
import luyao.wanandroid.ui.home.HomeFragment
import luyao.wanandroid.ui.login.LoginActivity
import luyao.wanandroid.ui.navigation.NavigationFragment
import luyao.wanandroid.ui.project.ProjectTypeFragment
import luyao.wanandroid.ui.share.ShareActivity
import luyao.wanandroid.ui.square.SquareFragment
import luyao.wanandroid.ui.system.SystemFragment
import luyao.wanandroid.util.Preference
import luyao.wanandroid.util.TOOL_URL

/**
 * Created by luyao
 * on 2019/5/7 15:36
 */
class MainFragment : BaseFragment() {

    private var isLogin by Preference(Preference.IS_LOGIN, false)
    private var userJson by Preference(Preference.USER_GSON, "")

    private val titleList = arrayOf("首页", "广场", "最新项目", "体系", "导航")
    private val fragmentList = arrayListOf<Fragment>()
    private val homeFragment by lazy { HomeFragment() } // 首页
    private val squareFragment by lazy { SquareFragment() } // 广场
    private val lastedProjectFragment by lazy { ProjectTypeFragment.newInstance(0, true) } // 最新项目
    private val systemFragment by lazy { SystemFragment() } // 体系
    private val navigationFragment by lazy { NavigationFragment() } // 导航
    private  var mOnPageChangeCallback: ViewPager2.OnPageChangeCallback? = null

    override fun getLayoutResId() = R.layout.activity_new_main

    init {
        fragmentList.add(homeFragment)
        fragmentList.add(squareFragment)
        fragmentList.add(lastedProjectFragment)
        fragmentList.add(systemFragment)
        fragmentList.add(navigationFragment)
    }

    override fun initView() {
        initViewPager()
        addFab.setOnClickListener {
            if (!isLogin) startKtxActivity<LoginActivity>()
            else startKtxActivity<ShareActivity>()
        }
    }

    override fun initData() {
    }

    private fun initViewPager() {
        viewPager.offscreenPageLimit = 1
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int) = fragmentList[position]

            override fun getItemCount() = titleList.size
        }


        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = titleList[position]
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        if (mOnPageChangeCallback == null) mOnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 1) addFab.show() else addFab.hide()
            }
        }
       mOnPageChangeCallback?.let { viewPager.registerOnPageChangeCallback(it) }
    }

    override fun onStop() {
        super.onStop()
        mOnPageChangeCallback?.let { viewPager.unregisterOnPageChangeCallback(it) }
    }


    private fun refreshView() {
//        navigationView.menu.findItem(R.id.nav_exit).isVisible = isLogin
        homeFragment.refresh()
        lastedProjectFragment.refresh()
    }

}
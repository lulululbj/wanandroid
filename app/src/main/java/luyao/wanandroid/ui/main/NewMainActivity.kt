package luyao.wanandroid.ui.main

import TOOL_URL
import android.content.Intent
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_new_main.*
import luyao.base.BaseNormalActivity
import luyao.wanandroid.R
import luyao.wanandroid.ui.BrowserNormalActivity
import luyao.wanandroid.ui.about.AboutActivity
import luyao.wanandroid.ui.collect.MyCollectActivity
import luyao.wanandroid.ui.home.HomeFragment
import luyao.wanandroid.ui.login.LoginActivity
import luyao.wanandroid.ui.navigation.NavigationFragment
import luyao.wanandroid.ui.project.ProjectActivity
import luyao.wanandroid.ui.project.ProjectTypeFragment
import luyao.wanandroid.ui.search.SearchActivity
import luyao.wanandroid.ui.system.SystemFragment
import luyao.wanandroid.util.Preference

/**
 * Created by luyao
 * on 2019/5/7 15:36
 */
class NewMainActivity : BaseNormalActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var isLogin by Preference(Preference.IS_LOGIN, false)
    private var userJson by Preference(Preference.USER_GSON, "")

    private val titleList = arrayOf("首页", "最新项目", "体系", "导航")
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
        mainToolBar.setNavigationOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.menu.findItem(R.id.nav_exit).isVisible = isLogin
    }

    override fun initData() {
        mainSearch.setOnClickListener { startActivity(SearchActivity::class.java) }
    }

    private fun initViewPager() {
        viewPager.offscreenPageLimit = titleList.size
        viewPager.adapter = object : androidx.fragment.app.FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int) = fragmentList[position]

            override fun getCount() = titleList.size

            override fun getPageTitle(position: Int) = titleList[position]

        }
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_blog -> startActivity(ProjectActivity::class.java,ProjectActivity.BLOG_TAG,true)
            R.id.nav_project_type -> startActivity(ProjectActivity::class.java,ProjectActivity.BLOG_TAG,false)
            R.id.nav_tool -> switchToTool()
            R.id.nav_collect -> switchCollect()
            R.id.nav_about -> switchAbout()
            R.id.nav_exit -> exit()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun exit() {
        MaterialDialog.Builder(this)
                .title("退出")
                .content("是否确认退出登录？")
                .positiveText("确认")
                .negativeText("取消")
                .onPositive { _, _ ->
                    isLogin = false
                    userJson = ""
                    navigationView.menu.findItem(R.id.nav_exit).isVisible = isLogin
                }.show()

    }

    private fun switchAbout() {
       startActivity(AboutActivity::class.java)
    }

    private fun switchCollect() {
        if (isLogin) {
            Intent(this, MyCollectActivity::class.java).run { startActivity(this) }
        } else {
            Intent(this, LoginActivity::class.java).run { startActivity(this) }
        }
    }

    private fun switchToTool() {
        Intent(this, BrowserNormalActivity::class.java).run {
            putExtra(BrowserNormalActivity.URL, TOOL_URL)
            startActivity(this)
        }
    }
}
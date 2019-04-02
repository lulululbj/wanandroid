package luyao.wanandroid.ui

import TOOL_URL
import android.content.Intent
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.title_layout.*
import luyao.wanandroid.R
import luyao.wanandroid.base.BaseActivity
import luyao.wanandroid.ui.collect.MyCollectActivity
import luyao.wanandroid.ui.home.HomeFragment
import luyao.wanandroid.ui.navigation.NavigationFragment
import luyao.wanandroid.ui.navigation.NavigationPresenter
import luyao.wanandroid.ui.project.ProjectFragment
import luyao.wanandroid.ui.project.ProjectPresenter
import luyao.wanandroid.ui.search.SearchActivity
import luyao.wanandroid.ui.system.SystemFragment
import luyao.wanandroid.ui.system.SystemPresenter

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var currentFragment: Fragment? = null
    private val homeFragment by lazy { HomeFragment() }
    private val systemFragment by lazy { SystemFragment() }
    private val navigationFragment by lazy { NavigationFragment() }
    private val projectFragment by lazy { ProjectFragment() }

//    private lateinit var homePresenter: HomePresenter
    private lateinit var systemPresenter: SystemPresenter
    private lateinit var navigationPresenter: NavigationPresenter
    private lateinit var projectPresenter: ProjectPresenter


    override fun getLayoutResId() = R.layout.activity_main

    override fun initView() {
        navigationView.setNavigationItemSelectedListener(this)

    }

    override fun initData() {
        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

//        homePresenter=HomePresenter(homeFragment)
        systemPresenter=SystemPresenter(systemFragment)
        navigationPresenter=NavigationPresenter(navigationFragment)
        projectPresenter=ProjectPresenter(projectFragment)
        switchFragment(homeFragment)
    }

    private fun switchFragment(targetFragment: Fragment) {
        val transition = supportFragmentManager.beginTransaction()
        if (!targetFragment.isAdded) {
            if (currentFragment != null) transition.hide(currentFragment!!)
            transition.add(R.id.mainLayout, targetFragment, targetFragment.javaClass.name)
        } else {
            transition.hide(currentFragment!!).show(targetFragment)
        }
        transition.commit()
        currentFragment = targetFragment
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> switchFragment(homeFragment)
            R.id.nav_system -> switchFragment(systemFragment)
            R.id.nav_navigation -> switchFragment(navigationFragment)
            R.id.nav_project -> switchFragment(projectFragment)
            R.id.nav_tool -> switchToTool()
            R.id.nav_collect -> Intent(this, MyCollectActivity::class.java).run { startActivity(this) }
        }
        mToolbar.title = item.title
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun switchToTool() {
        Intent(this, BrowserActivity::class.java).run {
            putExtra(BrowserActivity.URL, TOOL_URL)
            startActivity(this)
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        startActivity(SearchActivity::class.java)
        return true
    }
}

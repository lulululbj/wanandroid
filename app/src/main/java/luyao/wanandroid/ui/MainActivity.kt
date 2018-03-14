package luyao.wanandroid.ui

import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.title_layout.*
import luyao.gayhub.base.BaseActivity
import luyao.wanandroid.R
import luyao.wanandroid.ui.home.HomeFragment

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var currentFragment: Fragment? = null
    private val homeFragment by lazy { HomeFragment() }
    private val systemFragment by lazy { HomeFragment() }
    private val navigationFragment by lazy { HomeFragment() }
    private val projectFragment by lazy { HomeFragment() }
    private val toolFragment by lazy { HomeFragment() }

    override fun getLayoutResId() = R.layout.activity_main

    override fun initView() {
        navigationView.setNavigationItemSelectedListener(this)

    }

    override fun initData() {
        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        switchFragment(homeFragment)
    }

    private fun switchFragment(targetFragment: Fragment) {
        val transition = supportFragmentManager.beginTransaction()
        if (!targetFragment.isAdded) {
            if (currentFragment != null) transition.hide(currentFragment)
            transition.add(R.id.mainLayout, targetFragment, targetFragment.javaClass.name)
        } else {
            transition.hide(currentFragment).show(targetFragment)
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
            R.id.nav_tool -> switchFragment(toolFragment)
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }
}

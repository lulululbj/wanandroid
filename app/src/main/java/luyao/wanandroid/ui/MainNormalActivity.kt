//package luyao.wanandroid.ui
//
//import TOOL_URL
//import android.content.Intent
//import android.view.Menu
//import android.view.MenuItem
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.appcompat.app.ActionBarDrawerToggle
//import androidx.core.view.GravityCompat
//import com.afollestad.materialdialogs.MaterialDialog
//import com.bumptech.glide.Glide
//import com.bumptech.glide.request.RequestOptions
//import com.google.android.material.navigation.NavigationView
//import com.google.gson.Gson
//import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.android.synthetic.main.title_layout.*
//import luyao.base.BaseNormalActivity
//import luyao.wanandroid.App
//import luyao.wanandroid.R
//import luyao.wanandroid.model.bean.User
//import luyao.wanandroid.ui.about.AboutActivity
//import luyao.wanandroid.ui.collect.MyCollectActivity
//import luyao.wanandroid.ui.home.HomeFragment
//import luyao.wanandroid.ui.login.LoginActivity
//import luyao.wanandroid.ui.main.NewMainActivity
//import luyao.wanandroid.ui.navigation.NavigationFragment
//import luyao.wanandroid.ui.project.ProjectFragment
//import luyao.wanandroid.ui.project.ProjectTypeFragment
//import luyao.wanandroid.ui.search.SearchActivity
//import luyao.wanandroid.ui.system.SystemFragment
//import luyao.wanandroid.util.Preference
//
//class MainNormalActivity : BaseNormalActivity(), NavigationView.OnNavigationItemSelectedListener {
//
//    private var isLogin by Preference(Preference.IS_LOGIN, false)
//    private var userJson by Preference(Preference.USER_GSON, "")
//    private var currentFragment: androidx.fragment.app.Fragment? = null
//    private val homeFragment by lazy { HomeFragment() }
//    private val systemFragment by lazy { SystemFragment() }
//    private val blogFragment by lazy { ProjectFragment.newInstance(true) }
//    private val navigationFragment by lazy { NavigationFragment() }
//    private val projectFragment by lazy { ProjectFragment.newInstance(false) }
//    private val lastedProjectFragment by lazy { ProjectTypeFragment.newInstance(0, true) }
//
//    override fun getLayoutResId() = R.layout.activity_main
//
//    override fun initView() {
//        navigationView.setNavigationItemSelectedListener(this)
//
//        navigationView.menu.findItem(R.id.nav_exit).isVisible = isLogin
//    }
//
//    override fun initData() {
//        val toggle = ActionBarDrawerToggle(
//                this, drawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
//
//        switchFragment(homeFragment)
//
//        if (isLogin && userJson != "") {
//            App.CURRENT_USER = Gson().fromJson(userJson, User::class.java)
//            val user = App.CURRENT_USER
//            initUser(user)
//        }
//    }
//
//    private fun initUser(user: User) {
//        val navUserName: TextView = navigationView.getHeaderView(0).findViewById(R.id.navUserName)
//        val navAvatar: ImageView = navigationView.getHeaderView(0).findViewById(R.id.navAvatar)
//        navUserName.text = user.username
//        Glide.with(this).load(user.icon).apply(
//                RequestOptions().error(R.mipmap.ic_launcher)
//                        .placeholder(R.mipmap.ic_launcher)
//                        .fallback(R.mipmap.ic_launcher)
//        ).into(navAvatar)
//    }
//
//    private fun switchFragment(targetFragment: androidx.fragment.app.Fragment) {
//        val transition = supportFragmentManager.beginTransaction()
//        if (!targetFragment.isAdded) {
//            if (currentFragment != null) transition.hide(currentFragment!!)
//            transition.add(R.id.mainLayout, targetFragment, targetFragment.javaClass.name)
//        } else {
//            transition.hide(currentFragment!!).show(targetFragment)
//        }
//        transition.commit()
//        currentFragment = targetFragment
//
//
//    }
//
//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
////            R.id.nav_home -> switchFragment(homeFragment)
////            R.id.nav_lasted_project -> switchFragment(lastedProjectFragment)
////            R.id.nav_system -> switchFragment(systemFragment)
//            R.id.nav_blog -> switchFragment(blogFragment)
////            R.id.nav_navigation -> switchFragment(navigationFragment)
//            R.id.nav_project_type -> switchFragment(projectFragment)
//            R.id.nav_tool -> switchToTool()
//            R.id.nav_collect -> switchCollect()
//            R.id.nav_about -> switchAbout()
//            R.id.nav_exit -> exit()
//        }
//        mToolbar.title = item.title
//        drawerLayout.closeDrawer(GravityCompat.START)
//        return true
//    }
//
//    private fun exit() {
//        MaterialDialog.Builder(this)
//                .title("退出")
//                .content("是否确认退出登录？")
//                .positiveText("确认")
//                .negativeText("取消")
//                .onPositive { _, _ ->
//                    isLogin = false
//                    userJson = ""
//                    navigationView.menu.findItem(R.id.nav_exit).isVisible = isLogin
//                }.show()
//
//    }
//
//    private fun switchAbout() {
//        Intent(this, NewMainActivity::class.java).run { startActivity(this) }
//    }
//
//    private fun switchCollect() {
//        if (isLogin) {
//            Intent(this, MyCollectActivity::class.java).run { startActivity(this) }
//        } else {
//            Intent(this, LoginActivity::class.java).run { startActivity(this) }
//        }
//    }
//
//    private fun switchToTool() {
//        Intent(this, BrowserNormalActivity::class.java).run {
//            putExtra(BrowserNormalActivity.URL, TOOL_URL)
//            startActivity(this)
//        }
//    }
//
//    override fun onBackPressed() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START))
//            drawerLayout.closeDrawer(GravityCompat.START)
//        else
//            super.onBackPressed()
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_search, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        startActivity(SearchActivity::class.java)
//        return true
//    }
//}

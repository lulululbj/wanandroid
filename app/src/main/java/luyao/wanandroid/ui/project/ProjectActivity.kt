package luyao.wanandroid.ui.project

import kotlinx.android.synthetic.main.title_layout.*
import luyao.util.ktx.base.BaseActivity
import luyao.wanandroid.R

class ProjectActivity : BaseActivity() {

    companion object {
        const val BLOG_TAG = "isBlog"
    }

    private val isBlog by lazy { intent.getBooleanExtra(BLOG_TAG, false) }
    private var currentFragment: androidx.fragment.app.Fragment? = null

    override fun getLayoutResId() = R.layout.activity_project

    override fun initView() {
        mToolbar.title = if (isBlog) "公众号" else "项目分类"
        mToolbar.setNavigationIcon(R.drawable.arrow_back)
    }

    override fun initData() {
        mToolbar.setNavigationOnClickListener { onBackPressed() }

        switchFragment(ProjectFragment.newInstance(isBlog))
    }

    private fun switchFragment(targetFragment: androidx.fragment.app.Fragment) {
        val transition = supportFragmentManager.beginTransaction()
        if (!targetFragment.isAdded) {
            if (currentFragment != null) transition.hide(currentFragment!!)
            transition.add(R.id.content, targetFragment, targetFragment.javaClass.name)
        } else {
            transition.hide(currentFragment!!).show(targetFragment)
        }
        transition.commit()
        currentFragment = targetFragment
    }

}
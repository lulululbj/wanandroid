package luyao.wanandroid.ui.system

import kotlinx.android.synthetic.main.activity_system_detail.*
import luyao.util.ktx.base.BaseActivity
import luyao.wanandroid.R
import luyao.wanandroid.model.bean.SystemParent

/**
 * Created by Lu
 * on 2018/3/27 20:42
 */
class SystemTypeNormalActivity : BaseActivity() {

    companion object {
        const val ARTICLE_LIST = "article_list"
    }

    private val systemParent: SystemParent by lazy { intent.getSerializableExtra(ARTICLE_LIST) as SystemParent }

    override fun getLayoutResId() = R.layout.activity_system_detail

    override fun initView() {
        mToolbar.run {
            title = systemParent.name
            setNavigationIcon(R.drawable.arrow_back)
        }

        initViewPager()
    }


    override fun initData() {
        mToolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initViewPager() {
        viewPager.adapter = object : androidx.fragment.app.FragmentPagerAdapter(supportFragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getItem(position: Int) = SystemTypeFragment.newInstance(systemParent.children[position].id, false)

            override fun getCount() = systemParent.children.size

            override fun getPageTitle(position: Int) = systemParent.children[position].name

        }
        tabLayout.setupWithViewPager(viewPager)
    }
}
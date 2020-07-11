package luyao.wanandroid.ui.system


import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_system_detail.*
import luyao.mvvm.core.base.BaseActivity
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

        systemDetailViewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = systemParent.children.size

            override fun createFragment(position: Int) = SystemTypeFragment.newInstance(systemParent.children[position].id, false)
        }

        TabLayoutMediator(tabLayout, systemDetailViewPager) { tab, position ->
            tab.text = systemParent.children[position].name
        }.attach()

    }
}
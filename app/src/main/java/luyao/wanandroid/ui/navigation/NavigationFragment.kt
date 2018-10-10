package luyao.wanandroid.ui.navigation

import android.support.v7.widget.LinearLayoutManager
import dp2px
import kotlinx.android.synthetic.main.fragment_navigation.*
import luyao.wanandroid.R
import luyao.wanandroid.adapter.NavigationAdapter
import luyao.wanandroid.adapter.VerticalTabAdapter
import luyao.wanandroid.base.BaseFragment
import luyao.wanandroid.bean.Navigation
import luyao.wanandroid.view.SpaceItemDecoration
import q.rorbin.verticaltablayout.VerticalTabLayout
import q.rorbin.verticaltablayout.widget.TabView

/**
 * Created by Lu
 * on 2018/3/28 21:26
 */
class NavigationFragment : BaseFragment(), NavigationContract.View {

    private val navigationList = mutableListOf<Navigation>()
    private val tabAdapter by lazy { VerticalTabAdapter(navigationList.map { it.name }) }
    private val navigationAdapter by lazy { NavigationAdapter() }
    private val mLayoutManager by lazy { LinearLayoutManager(activity) }
    override lateinit var mPresenter: NavigationContract.Presenter

    override fun getLayoutResId() = R.layout.fragment_navigation

    override fun initView() {
        navigationRecycleView.run {
            layoutManager = mLayoutManager
            addItemDecoration(SpaceItemDecoration(this.dp2px(10f)))
            adapter = navigationAdapter
        }

        initTabLayout()
    }

    private fun initTabLayout() {
        tabLayout.addOnTabSelectedListener(object : VerticalTabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabView?, position: Int) {
            }

            override fun onTabSelected(tab: TabView?, position: Int) {
                scrollToPosition(position)
            }
        })
    }

    private fun scrollToPosition(position: Int) {
        val firstPotion = mLayoutManager.findFirstVisibleItemPosition()
        val lastPosition = mLayoutManager.findLastVisibleItemPosition()
        when {
            position <= firstPotion || position >= lastPosition -> navigationRecycleView.smoothScrollToPosition(position)
            else -> navigationRecycleView.run {
                smoothScrollBy(0, this.getChildAt(position - firstPotion).top-this.dp2px(8f))
            }
        }
    }

    override fun initData() {
        mPresenter.getNavigation()
    }

    override fun getNavigation(navigationList: List<Navigation>) {
        this.navigationList.clear()
        this.navigationList.addAll(navigationList)
        tabLayout.setTabAdapter(tabAdapter)

        navigationAdapter.setNewData(navigationList)
    }

}
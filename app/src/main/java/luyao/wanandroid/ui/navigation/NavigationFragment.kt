package luyao.wanandroid.ui.navigation

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_navigation.*
import luyao.util.ktx.base.BaseVMFragment
import luyao.util.ktx.ext.dp2px
import luyao.util.ktx.ext.toast
import luyao.wanandroid.R
import luyao.wanandroid.adapter.NavigationAdapter
import luyao.wanandroid.adapter.VerticalTabAdapter
import luyao.wanandroid.model.bean.Navigation
import luyao.wanandroid.view.SpaceItemDecoration
import onNetError
import q.rorbin.verticaltablayout.VerticalTabLayout
import q.rorbin.verticaltablayout.widget.TabView
import retrofit2.HttpException

/**
 * Created by Lu
 * on 2018/3/28 21:26
 */
class NavigationFragment : BaseVMFragment<NavigationViewModel>() {

    override fun providerVMClass(): Class<NavigationViewModel>? = NavigationViewModel::class.java

    private val navigationList = mutableListOf<Navigation>()
    private val tabAdapter by lazy { VerticalTabAdapter(navigationList.map { it.name }) }
    private val navigationAdapter by lazy { NavigationAdapter() }
    private val mLayoutManager by lazy { LinearLayoutManager(activity) }

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
                smoothScrollBy(0, this.getChildAt(position - firstPotion).top - this.dp2px(8f))
            }
        }
    }

    override fun initData() {
        mViewModel.getNavigation()
    }

    private fun getNavigation(navigationList: List<Navigation>) {
        this.navigationList.clear()
        this.navigationList.addAll(navigationList)
        tabLayout.setTabAdapter(tabAdapter)

        navigationAdapter.setNewData(navigationList)
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.run {
            mNavigationList.observe(this@NavigationFragment, Observer {
                it?.run { getNavigation(it) }
            })
        }
    }

    override fun onError(e: Throwable) {
        super.onError(e)

        activity?.onNetError(e) {

        }
    }

}
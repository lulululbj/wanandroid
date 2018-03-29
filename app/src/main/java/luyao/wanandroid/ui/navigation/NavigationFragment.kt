package luyao.wanandroid.ui.navigation

import android.support.v7.widget.LinearLayoutManager
import dp2px
import kotlinx.android.synthetic.main.fragment_navigation.*
import luyao.gayhub.base.BaseMvpFragment
import luyao.wanandroid.R
import luyao.wanandroid.adapter.NavigationAdapter
import luyao.wanandroid.adapter.VerticalTabAdapter
import luyao.wanandroid.bean.Navigation
import luyao.wanandroid.view.SpaceItemDecoration

/**
 * Created by Lu
 * on 2018/3/28 21:26
 */
class NavigationFragment : BaseMvpFragment<NavigationContract.View, NavigationPresenter>(), NavigationContract.View {

    private val navigationList = mutableListOf<Navigation>()
    private val tabAdapter by lazy { VerticalTabAdapter(navigationList.map { it.name }) }
    private val navigationAdapter by lazy { NavigationAdapter() }
    override var mPresenter = NavigationPresenter()

    override fun getLayoutResId() = R.layout.fragment_navigation

    override fun initView() {
        navigationRecycleView.run {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(SpaceItemDecoration(this.dp2px(10f)))
            adapter = navigationAdapter
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
package luyao.wanandroid.ui.navigation

import kotlinx.android.synthetic.main.fragment_navigation.*
import luyao.gayhub.base.BaseMvpFragment
import luyao.wanandroid.R
import luyao.wanandroid.adapter.VerticalTabAdapter
import luyao.wanandroid.bean.Navigation

/**
 * Created by Lu
 * on 2018/3/28 21:26
 */
class NavigationFragment : BaseMvpFragment<NavigationContract.View, NavigationPresenter>(), NavigationContract.View {

    private val navigationList = mutableListOf<Navigation>()
    private val tabAdapter by lazy { VerticalTabAdapter(navigationList.map { it.name }) }
    override var mPresenter = NavigationPresenter()

    override fun getLayoutResId() = R.layout.fragment_navigation

    override fun initView() {
    }

    override fun initData() {
        mPresenter.getNavigation()
    }

    override fun getNavigation(navigationList: List<Navigation>) {
        this.navigationList.clear()
        this.navigationList.addAll(navigationList)
        tabLayout.setTabAdapter(tabAdapter)
    }

}
package luyao.wanandroid.ui.system

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import dp2px
import kotlinx.android.synthetic.main.fragment_system.*
import luyao.gayhub.base.BaseMvpFragment
import luyao.wanandroid.R
import luyao.wanandroid.adapter.SystemAdapter
import luyao.wanandroid.bean.SystemParent
import luyao.wanandroid.view.SpaceItemDecoration

/**
 * Created by Lu
 * on 2018/3/26 21:11
 */
class SystemFragment : BaseMvpFragment<SystemContract.View, SystemPresenter>(), SystemContract.View {

    private val systemAdapter by lazy { SystemAdapter() }
    override var mPresenter = SystemPresenter()

    override fun getLayoutResId() = R.layout.fragment_system

    override fun initView() {
        systemRecycleView.run {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(SpaceItemDecoration(systemRecycleView.dp2px(10f)))
            adapter = systemAdapter
        }

        systemRefreshLayout.run {
            isRefreshing = true
            setOnRefreshListener { refresh() }
        }

        refresh()
    }

    private fun refresh() {
        mPresenter.getSystemTypes()
    }

    override fun initData() {
        systemAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->

        }
    }

    override fun getSystemTypes(systemList: List<SystemParent>) {
        systemRefreshLayout.isRefreshing = false
        systemAdapter.setNewData(systemList)
    }
}
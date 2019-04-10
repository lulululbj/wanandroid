package luyao.wanandroid.ui.system

import androidx.lifecycle.Observer
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import dp2px
import kotlinx.android.synthetic.main.fragment_system.*
import luyao.base.BaseFragment
import luyao.wanandroid.R
import luyao.wanandroid.adapter.SystemAdapter
import luyao.wanandroid.view.SpaceItemDecoration

/**
 * Created by Lu
 * on 2018/3/26 21:11
 */
class SystemFragment : BaseFragment<SystemViewModel>() {

    override fun providerVMClass(): Class<SystemViewModel>? = SystemViewModel::class.java
    private val systemAdapter by lazy { SystemAdapter() }

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
        mViewModel.getSystemTypes()
    }

    override fun initData() {
        systemAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
            Intent(activity, SystemTypeActivity::class.java).run {
                putExtra(SystemTypeActivity.ARTICLE_LIST, systemAdapter.data[position])
                startActivity(this)
            }
        }
    }

    override fun startObserve() {
        mViewModel.run {
            mSystemParentList.observe(this@SystemFragment, Observer {
                it?.run {
                    systemRefreshLayout.isRefreshing = false
                    systemAdapter.setNewData(it)
                }
            })
        }
    }
}
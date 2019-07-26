package luyao.wanandroid.ui.system

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_system.*
import luyao.util.ktx.base.BaseVMFragment
import luyao.util.ktx.ext.dp2px
import luyao.util.ktx.ext.startKtxActivity
import luyao.wanandroid.R
import luyao.wanandroid.adapter.SystemAdapter
import luyao.wanandroid.view.SpaceItemDecoration
import onNetError

/**
 * Created by Lu
 * on 2018/3/26 21:11
 */
class SystemFragment : BaseVMFragment<SystemViewModel>() {

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
            startKtxActivity<SystemTypeNormalActivity>(value = SystemTypeNormalActivity.ARTICLE_LIST to systemAdapter.data[position])
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.run {
            mSystemParentList.observe(this@SystemFragment, Observer {
                it?.run {
                    systemRefreshLayout.isRefreshing = false
                    systemAdapter.setNewData(it)
                }
            })
        }
    }

    override fun onError(e: Throwable) {
        super.onError(e)

        activity?.onNetError(e) {
            systemRefreshLayout.isRefreshing = false
        }
    }
}
package luyao.wanandroid.ui.system

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_system.*
import luyao.util.ktx.base.BaseVMFragment
import luyao.util.ktx.ext.dp2px
import luyao.util.ktx.ext.startKtxActivity
import luyao.util.ktx.ext.toast
import luyao.wanandroid.BR
import luyao.wanandroid.R
import luyao.wanandroid.adapter.BaseBindAdapter
import luyao.wanandroid.model.bean.SystemParent
import luyao.wanandroid.view.SpaceItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 体系
 * Created by Lu
 * on 2018/3/26 21:11
 */
class SystemFragment : BaseVMFragment<SystemViewModel>() {

    private val mViewModel: SystemViewModel by viewModel()
    private val systemAdapter by lazy { BaseBindAdapter<SystemParent>(R.layout.item_system, BR.systemParent) }

    override fun getLayoutResId() = R.layout.fragment_system

    override fun initView() {
        initRecycleView()
    }

    override fun initData() {
        refresh()
    }

    private fun initRecycleView() {
        systemRecycleView.run {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(SpaceItemDecoration(systemRecycleView.dp2px(10)))
            adapter = systemAdapter
        }

        systemAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
            startKtxActivity<SystemTypeNormalActivity>(value = SystemTypeNormalActivity.ARTICLE_LIST to systemAdapter.data[position])
        }

        systemRefreshLayout.setOnRefreshListener { refresh() }
    }

    private fun refresh() {
        mViewModel.getSystemTypes()
    }


    override fun startObserve() {
        mViewModel.run {
            uiState.observe(this@SystemFragment, Observer {
                systemRefreshLayout.isRefreshing = it.showLoading

                it.showSuccess?.let { list -> systemAdapter.replaceData(list) }

                it.showError?.let { message -> activity?.toast(message) }
            })
        }
    }
}
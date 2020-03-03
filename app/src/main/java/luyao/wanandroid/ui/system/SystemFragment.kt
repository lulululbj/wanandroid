package luyao.wanandroid.ui.system

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_system.*
import luyao.util.ktx.ext.dp2px
import luyao.util.ktx.ext.startKtxActivity
import luyao.util.ktx.ext.toast
import luyao.wanandroid.BR
import luyao.wanandroid.R
import luyao.wanandroid.adapter.BaseBindAdapter
import luyao.wanandroid.databinding.FragmentSystemBinding
import luyao.wanandroid.model.bean.SystemParent
import luyao.wanandroid.view.SpaceItemDecoration
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * 体系
 * Created by Lu
 * on 2018/3/26 21:11
 */
class SystemFragment : luyao.mvvm.core.base.BaseVMFragment<SystemViewModel>() {

    override fun initVM(): SystemViewModel = getViewModel()

    private val systemAdapter by lazy { BaseBindAdapter<SystemParent>(R.layout.item_system, BR.systemParent) }

    override fun getLayoutResId() = R.layout.fragment_system

    override fun initView() {
        (mBinding as FragmentSystemBinding).viewModel = mViewModel
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
            uiState.observe(viewLifecycleOwner, Observer {
                //                systemRefreshLayout.isRefreshing = it.showLoading

                it.showSuccess?.let { list -> systemAdapter.replaceData(list) }

                it.showError?.let { message -> activity?.toast(message) }
            })
        }
    }
}
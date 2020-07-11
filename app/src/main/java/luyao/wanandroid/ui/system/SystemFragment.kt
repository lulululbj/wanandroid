package luyao.wanandroid.ui.system

import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_system.*
import luyao.mvvm.core.base.BaseVMFragment
import luyao.util.ktx.ext.startKtxActivity
import luyao.util.ktx.ext.toast
import luyao.wanandroid.BR
import luyao.wanandroid.R
import luyao.wanandroid.adapter.BaseBindAdapter
import luyao.wanandroid.databinding.FragmentSystemBinding
import luyao.wanandroid.model.bean.SystemParent
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 体系
 * Created by Lu
 * on 2018/3/26 21:11
 */
class SystemFragment : BaseVMFragment<FragmentSystemBinding>(R.layout.fragment_system) {

    private val systemViewModel by viewModel<SystemViewModel>()
    private val systemAdapter by lazy { BaseBindAdapter<SystemParent>(R.layout.item_system, BR.systemParent) }

    override fun initView() {
        binding.run {
            viewModel = systemViewModel
            adapter = systemAdapter
        }
        initRecycleView()
    }

    override fun initData() {
        refresh()
    }

    private fun initRecycleView() {

        systemAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
            startKtxActivity<SystemTypeNormalActivity>(value = SystemTypeNormalActivity.ARTICLE_LIST to systemAdapter.data[position])
        }

        systemRefreshLayout.setOnRefreshListener { refresh() }
    }

    private fun refresh() {
        systemViewModel.getSystemTypes()
    }


    override fun startObserve() {
        systemViewModel.run {
            uiState.observe(viewLifecycleOwner, Observer {
                //                systemRefreshLayout.isRefreshing = it.showLoading

                it.showSuccess?.let { list -> systemAdapter.replaceData(list) }

                it.showError?.let { message -> activity?.toast(message) }
            })
        }
    }
}
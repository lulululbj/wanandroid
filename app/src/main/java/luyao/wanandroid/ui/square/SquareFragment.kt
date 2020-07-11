package luyao.wanandroid.ui.square

import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_square.*
import luyao.mvvm.core.base.BaseVMFragment
import luyao.util.ktx.ext.startKtxActivity
import luyao.util.ktx.ext.toast
import luyao.wanandroid.BR
import luyao.wanandroid.R
import luyao.wanandroid.adapter.BaseBindAdapter
import luyao.wanandroid.databinding.FragmentSquareBinding
import luyao.wanandroid.model.bean.Article
import luyao.wanandroid.ui.BrowserActivity
import luyao.wanandroid.view.CustomLoadMoreView
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by luyao
 * on 2019/10/15 10:18
 */
class SquareFragment : BaseVMFragment<FragmentSquareBinding>(R.layout.fragment_square) {

    private val articleViewModel by viewModel<ArticleViewModel>()

    private val squareAdapter by lazy { BaseBindAdapter<Article>(R.layout.item_square_constraint, BR.article) }

    override fun initView() {
        binding.run {
            viewModel = articleViewModel
            adapter = squareAdapter
        }
        initRecycleView()
    }

    override fun initData() {
        refresh()
    }

    private fun initRecycleView() {
        squareAdapter.run {
            setOnItemClickListener { _, _, position ->
                startKtxActivity<BrowserActivity>(value = BrowserActivity.URL to squareAdapter.data[position].link)
            }
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({ loadMore() }, squareRecycleView)
        }
    }

    private fun loadMore() {
        articleViewModel.getSquareArticleList(false)
    }

    fun refresh() {
        articleViewModel.getSquareArticleList(true)
    }

    override fun startObserve() {
        articleViewModel.uiState.observe(viewLifecycleOwner, Observer {

            it.showSuccess?.let { list ->
                squareAdapter.run {
                    setEnableLoadMore(false)
                    if (it.isRefresh) replaceData(list.datas)
                    else addData(list.datas)
                    setEnableLoadMore(true)
                    loadMoreComplete()
                }
            }

            if (it.showEnd) squareAdapter.loadMoreEnd()

            it.needLogin?.let { needLogin ->
                if (needLogin) Navigation.findNavController(squareRecycleView).navigate(R.id.action_tab_to_login)
                else Navigation.findNavController(squareRecycleView).navigate(R.id.action_tab_to_share)
            }

            it.showError?.let { message ->
                activity?.toast(if (message.isBlank()) "网络异常" else message)
            }

        })
    }

}
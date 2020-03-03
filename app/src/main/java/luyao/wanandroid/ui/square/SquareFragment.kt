package luyao.wanandroid.ui.square

import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_square.*
import luyao.util.ktx.ext.dp2px
import luyao.util.ktx.ext.startKtxActivity
import luyao.util.ktx.ext.toast
import luyao.wanandroid.BR
import luyao.wanandroid.R
import luyao.wanandroid.adapter.BaseBindAdapter
import luyao.wanandroid.databinding.FragmentSquareBinding
import luyao.wanandroid.model.bean.Article
import luyao.wanandroid.ui.BrowserActivity
import luyao.wanandroid.view.CustomLoadMoreView
import luyao.wanandroid.view.SpaceItemDecoration
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * Created by luyao
 * on 2019/10/15 10:18
 */
class SquareFragment : luyao.mvvm.core.base.BaseVMFragment<ArticleViewModel>() {

    override fun initVM(): ArticleViewModel = getViewModel()

    private val squareAdapter by lazy { BaseBindAdapter<Article>(R.layout.item_square_constraint, BR.article) }

    override fun getLayoutResId() = R.layout.fragment_square


    override fun initView() {
        (mBinding as FragmentSquareBinding).viewModel = mViewModel
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
        squareRecycleView.run {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(SpaceItemDecoration(squareRecycleView.dp2px(10)))
            adapter = squareAdapter
        }
    }

    private fun loadMore() {
        mViewModel.getSquareArticleList(false)
    }

    fun refresh() {
        mViewModel.getSquareArticleList(true)
    }

    override fun startObserve() {
        mViewModel.uiState.observe(viewLifecycleOwner, Observer {

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
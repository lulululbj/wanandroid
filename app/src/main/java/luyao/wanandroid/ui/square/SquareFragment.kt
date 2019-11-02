package luyao.wanandroid.ui.square

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_square.*
import luyao.util.ktx.base.BaseVMFragment
import luyao.util.ktx.ext.dp2px
import luyao.util.ktx.ext.startKtxActivity
import luyao.util.ktx.ext.toast
import luyao.wanandroid.BR
import luyao.wanandroid.R
import luyao.wanandroid.adapter.BaseBindAdapter
import luyao.wanandroid.model.bean.Article
import luyao.wanandroid.ui.BrowserNormalActivity
import luyao.wanandroid.ui.login.LoginActivity
import luyao.wanandroid.ui.share.ShareActivity
import luyao.wanandroid.view.CustomLoadMoreView
import luyao.wanandroid.view.SpaceItemDecoration

/**
 * Created by luyao
 * on 2019/10/15 10:18
 */
class SquareFragment : BaseVMFragment<ArticleViewModel>() {

    private val squareAdapter by lazy { BaseBindAdapter<Article>(R.layout.item_square, BR.article) }

    override fun providerVMClass() = ArticleViewModel::class.java

    override fun getLayoutResId() = R.layout.fragment_square


    override fun initView() {
        initRecycleView()
        squareRefreshLayout.setOnRefreshListener { refresh() }
    }

    override fun initData() {
        refresh()
    }

    private fun initRecycleView() {
        squareAdapter.run {
            setOnItemClickListener { _, _, position ->
                startKtxActivity<BrowserNormalActivity>(value = BrowserNormalActivity.URL to squareAdapter.data[position].link)
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
        squareAdapter.setEnableLoadMore(false)
        mViewModel.getSquareArticleList(true)
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.uiState.observe(this, Observer {

            squareRefreshLayout.isRefreshing = it.showLoading

            it.showSuccess?.let { list ->
                squareAdapter.run {
                    if (it.isRefresh) replaceData(list.datas)
                    else addData(list.datas)
                    setEnableLoadMore(true)
                    loadMoreComplete()
                }
            }

            if (it.showEnd) squareAdapter.loadMoreEnd()

            it.needLogin?.let { needLogin ->
                if (needLogin) startKtxActivity<LoginActivity>()
                else startKtxActivity<ShareActivity>()
            }

           it.showError?.let { message ->
               activity?.toast(if (message.isBlank()) "网络异常" else message)
           }

        })
    }

}
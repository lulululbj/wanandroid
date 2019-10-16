package luyao.wanandroid.ui.square

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.input.input
import kotlinx.android.synthetic.main.fragment_square.*
import luyao.util.ktx.base.BaseVMFragment
import luyao.util.ktx.ext.dp2px
import luyao.util.ktx.ext.startKtxActivity
import luyao.wanandroid.R
import luyao.wanandroid.adapter.SquareAdapter
import luyao.wanandroid.ui.BrowserNormalActivity
import luyao.wanandroid.ui.login.LoginActivity
import luyao.wanandroid.ui.share.ShareActivity
import luyao.wanandroid.view.CustomLoadMoreView
import luyao.wanandroid.view.SpaceItemDecoration

/**
 * Created by luyao
 * on 2019/10/15 10:18
 */
class SquareFragment : BaseVMFragment<SquareViewModel>() {

    private val squareAdapter by lazy { SquareAdapter() }

    override fun providerVMClass() = SquareViewModel::class.java

    override fun getLayoutResId() = R.layout.fragment_square


    override fun initView() {
        initRecycleView()
        squareRefreshLayout.setOnRefreshListener { refresh() }
        addFab.setOnClickListener { mViewModel.checkLogin() }
    }

    override fun initData() {
        loadMore()
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
        mViewModel.getSquareArticleList(true);
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

            it.needLogin?.let {needLogin ->
                if (needLogin) startKtxActivity<LoginActivity>()
                else startKtxActivity<ShareActivity>()
            }

        })
    }

}
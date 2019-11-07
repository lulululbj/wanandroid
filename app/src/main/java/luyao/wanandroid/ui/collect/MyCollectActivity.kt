package luyao.wanandroid.ui.collect

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_collect.*
import kotlinx.android.synthetic.main.title_layout.*
import luyao.util.ktx.base.BaseVMActivity
import luyao.util.ktx.ext.dp2px
import luyao.util.ktx.ext.startKtxActivity
import luyao.util.ktx.ext.toast
import luyao.wanandroid.R
import luyao.wanandroid.adapter.HomeArticleAdapter
import luyao.wanandroid.ui.BrowserNormalActivity
import luyao.wanandroid.ui.square.ArticleViewModel
import luyao.wanandroid.view.CustomLoadMoreView
import luyao.wanandroid.view.SpaceItemDecoration

/**
 * Created by Lu
 * on 2018/4/10 22:09
 */
class MyCollectActivity : BaseVMActivity<ArticleViewModel>() {

    override fun providerVMClass() = ArticleViewModel::class.java

    private val articleAdapter by lazy { HomeArticleAdapter() }

    override fun getLayoutResId() = R.layout.activity_collect

    override fun initView() {
        mToolbar.title = getString(R.string.my_collect)
        mToolbar.setNavigationIcon(R.drawable.arrow_back)

        collectRecycleView.run {
            layoutManager = LinearLayoutManager(this@MyCollectActivity)
            addItemDecoration(SpaceItemDecoration(collectRecycleView.dp2px(10)))
        }

        initAdapter()

        collectRefreshLayout.run {
            setOnRefreshListener { refresh() }
        }
    }

    override fun initData() {
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        refresh()
    }

    private fun refresh() {
        articleAdapter.setEnableLoadMore(false)
        mViewModel.getCollectArticleList(true)
    }

    private fun initAdapter() {
        articleAdapter.run {
            //            showStar(false)
            setOnItemClickListener { _, _, position ->
                startKtxActivity<BrowserNormalActivity>(value = BrowserNormalActivity.URL to articleAdapter.data[position].link)
            }
            onItemChildClickListener = itemChildClickListener
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({ loadMore() }, collectRecycleView)
        }
        collectRecycleView.adapter = articleAdapter
    }

    private val itemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
        when (view.id) {
            R.id.articleStar -> {
                articleAdapter.run {
                    data[position].run {
                        collect = !collect
                        mViewModel.collectArticle(originId, collect)
                    }
                    notifyItemRemoved(position)
                }
            }
        }
    }

    private fun loadMore() {
        mViewModel.getCollectArticleList(false)
    }

    override fun startObserve() {

        mViewModel.apply {

            uiState.observe(this@MyCollectActivity, Observer {
                collectRefreshLayout.isRefreshing = it.showLoading

                it.showSuccess?.let { list ->
                    list.datas.forEach { it.collect = true }
                    articleAdapter.run {
                        if (it.isRefresh) replaceData(list.datas)
                        else addData(list.datas)
                        setEnableLoadMore(true)
                        loadMoreComplete()
                    }
                }

                if (it.showEnd) articleAdapter.loadMoreEnd()

                it.showError?.let { message ->
                    toast(if (message.isBlank()) "网络异常" else message)
                }
            })
        }
    }

}
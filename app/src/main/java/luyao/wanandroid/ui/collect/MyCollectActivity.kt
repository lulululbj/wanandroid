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
import luyao.wanandroid.model.bean.ArticleList
import luyao.wanandroid.ui.BrowserNormalActivity
import luyao.wanandroid.ui.login.LoginActivity
import luyao.wanandroid.view.CustomLoadMoreView
import luyao.wanandroid.view.SpaceItemDecoration

/**
 * Created by Lu
 * on 2018/4/10 22:09
 */
class MyCollectActivity : BaseVMActivity<CollectViewModel>() {

    override fun providerVMClass() = CollectViewModel::class.java

    private val articleAdapter by lazy { HomeArticleAdapter() }
    private var currentPage = 0

    override fun getLayoutResId() = R.layout.activity_collect

    override fun initView() {
        mToolbar.title = getString(R.string.my_collect)
        mToolbar.setNavigationIcon(R.drawable.arrow_back)

        collectRecycleView.run {
            layoutManager = LinearLayoutManager(this@MyCollectActivity)
            addItemDecoration(SpaceItemDecoration(collectRecycleView.dp2px(10f)))
        }

        initAdapter()

        collectRefreshLayout.run {
            setOnRefreshListener { refresh() }
            isRefreshing = true
        }
        refresh()
    }

    private fun refresh() {
        articleAdapter.setEnableLoadMore(false)
        collectRefreshLayout.isRefreshing = true
        currentPage = 0
        mViewModel.getCollectArticles(currentPage)
    }

    private fun initAdapter() {
        articleAdapter.run {
            showStar(false)
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
                        mViewModel.collectArticle(id, collect)
                    }
                    notifyDataSetChanged()
                }
            }
        }
    }

    private fun loadMore() {
        mViewModel.getCollectArticles(currentPage)
    }

    override fun initData() {
        mToolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun getCollectArticles(articleList: ArticleList) {
        articleAdapter.run {

            //            if (articleList.datas.isEmpty()) {
//                replaceData(articleList.datas)
//                return
//            }
            if (articleList.offset >= articleList.total) {
                loadMoreEnd()
                return
            }

            if (collectRefreshLayout.isRefreshing) replaceData(articleList.datas)
            else addData(articleList.datas)
            setEnableLoadMore(true)
            loadMoreComplete()
        }
        collectRefreshLayout.isRefreshing = false
        currentPage++
    }

    override fun startObserve() {
        mViewModel.run {
            mArticleList.observe(this@MyCollectActivity, Observer {
                it?.run {
                    getCollectArticles(it)
                }
            })

            mErrorMsg.observe(this@MyCollectActivity, Observer {
                it?.run {
                    collectRefreshLayout.isRefreshing = false
                    toast(it)
                    startKtxActivity<LoginActivity>()
                    finish()
                }
            })
        }
    }

}
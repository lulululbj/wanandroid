package luyao.wanandroid.ui.collect

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import dp2px
import kotlinx.android.synthetic.main.activity_collect.*
import kotlinx.android.synthetic.main.title_layout.*
import luyao.wanandroid.R
import luyao.wanandroid.adapter.HomeArticleAdapter
import luyao.wanandroid.base.BaseActivity
import luyao.wanandroid.bean.ArticleList
import luyao.wanandroid.ui.BrowserActivity
import luyao.wanandroid.ui.login.LoginActivity
import luyao.wanandroid.view.CustomLoadMoreView
import luyao.wanandroid.view.SpaceItemDecoration
import toast

/**
 * Created by Lu
 * on 2018/4/10 22:09
 */
class MyCollectActivity : BaseActivity(), CollectContract.View {

    private val articleAdapter by lazy { HomeArticleAdapter() }
    private var currentPage = 0

    override var mPresenter: CollectContract.Presenter = CollectPresenter(this)

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
        mPresenter.getCollectArticles(currentPage)
    }

    private fun initAdapter() {
        articleAdapter.run {
            setOnItemClickListener { _, _, position ->
                Intent(this@MyCollectActivity, BrowserActivity::class.java).run {
                    putExtra(BrowserActivity.URL, articleAdapter.data[position].link)
                    startActivity(this)
                }

            }
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({ loadMore() }, collectRecycleView)
        }
        collectRecycleView.adapter = articleAdapter
    }

    private fun loadMore() {
        mPresenter.getCollectArticles(currentPage)
    }

    override fun initData() {
        mToolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun getCollectArticles(articleList: ArticleList) {
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

    override fun getCollectArticlesError(message: String) {
        collectRefreshLayout.isRefreshing = false
        toast(message)
        startActivity(LoginActivity::class.java)
        finish()
    }
}
package luyao.wanandroid.ui.system

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import dp2px
import kotlinx.android.synthetic.main.fragment_systemtype.*
import luyao.gayhub.base.BaseMvpFragment
import luyao.wanandroid.R
import luyao.wanandroid.adapter.HomeArticleAdapter
import luyao.wanandroid.bean.ArticleList
import luyao.wanandroid.bean.SystemParent
import luyao.wanandroid.ui.BrowserActivity
import luyao.wanandroid.view.CustomLoadMoreView
import luyao.wanandroid.view.SpaceItemDecoration

/**
 * Created by Lu
 * on 2018/3/27 21:36
 */
class SystemTypeFragment : BaseMvpFragment<SystemContract.View, SystemPresenter>(), SystemContract.View {

    private val cid by lazy { arguments?.getInt(CID) }
    private val systemTypeAdapter by lazy { HomeArticleAdapter() }
    private var currentPage = 0
    override var mPresenter = SystemPresenter()

    companion object {

        private const val CID = "cid"
        fun newInstance(cid: Int): SystemTypeFragment {
            val fragment = SystemTypeFragment()
            val bundle = Bundle()
            bundle.putInt(CID, cid)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun getLayoutResId() = R.layout.fragment_systemtype

    override fun initView() {

        initRecycleView()


        typeRefreshLayout.run {
            isRefreshing = true
            setOnRefreshListener { refresh() }
        }
        refresh()
    }

    private fun initRecycleView() {

        systemTypeAdapter.run {
            setOnItemClickListener { _, _, position ->
                val intent = Intent(activity, BrowserActivity::class.java)
                intent.putExtra(BrowserActivity.URL, systemTypeAdapter.data[position].link)
                startActivity(intent)
            }
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({ loadMore() }, typeRecycleView)
        }
        typeRecycleView.run {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(SpaceItemDecoration(typeRecycleView.dp2px(10f)))
            adapter = systemTypeAdapter
        }
    }

    private fun loadMore() {
        cid?.let {
            mPresenter.getSystemTypeDetail(it, currentPage)
        }
    }

    private fun refresh() {
        systemTypeAdapter.setEnableLoadMore(false)
        typeRefreshLayout.isRefreshing = true
        currentPage = 0
        cid?.let {
            mPresenter.getSystemTypeDetail(it, currentPage)
        }
    }

    override fun initData() {
    }

    override fun getSystemTypeDetail(articleList: ArticleList) {
        systemTypeAdapter.run {
            if (articleList.offset >= articleList.total) {
                loadMoreEnd()
                return
            }

            if (typeRefreshLayout.isRefreshing) replaceData(articleList.datas)
            else addData(articleList.datas)
            setEnableLoadMore(true)
            loadMoreComplete()
        }
        typeRefreshLayout.isRefreshing = false
        currentPage++
    }

    override fun getSystemTypes(systemList: List<SystemParent>) {
    }
}
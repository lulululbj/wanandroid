package luyao.wanandroid.ui.project

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import dp2px
import kotlinx.android.synthetic.main.fragment_projecttype.*
import kotlinx.android.synthetic.main.fragment_systemtype.*
import luyao.gayhub.base.BaseMvpFragment
import luyao.wanandroid.R
import luyao.wanandroid.adapter.ProjectAdapter
import luyao.wanandroid.bean.ArticleList
import luyao.wanandroid.bean.SystemParent
import luyao.wanandroid.ui.BrowserActivity
import luyao.wanandroid.view.CustomLoadMoreView
import luyao.wanandroid.view.SpaceItemDecoration

/**
 * Created by Lu
 * on 2018/4/1 17:06
 */
class ProjectTypeFragment : BaseMvpFragment<ProjectContract.View, ProjectPresenter>(), ProjectContract.View {

    private val cid by lazy { arguments?.getInt(ProjectTypeFragment.CID) }
    private var currentPage = 1
    private val projectAdapter by lazy { ProjectAdapter() }
    override var mPresenter = ProjectPresenter()

    override fun getLayoutResId() = R.layout.fragment_projecttype

    companion object {

        private const val CID = "projectCid"
        fun newInstance(cid: Int): ProjectTypeFragment {
            val fragment = ProjectTypeFragment()
            val bundle = Bundle()
            bundle.putInt(CID, cid)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView() {
        initRecycleView()


        projectRefreshLayout.run {
            isRefreshing = true
            setOnRefreshListener { refresh() }
        }
        refresh()
    }

    private fun refresh() {
        projectAdapter.setEnableLoadMore(false)
        projectRefreshLayout.isRefreshing = true
        currentPage = 1
        cid?.let {
            mPresenter.getProjectTypeDetailList(currentPage, it)
        }
    }

    private fun initRecycleView() {
        projectAdapter.run {
            setOnItemClickListener { _, _, position ->
                val intent = Intent(activity, BrowserActivity::class.java)
                intent.putExtra(BrowserActivity.URL, projectAdapter.data[position].link)
                startActivity(intent)
            }
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({ loadMore() }, typeRecycleView)
        }
        projectRecycleView.run {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(SpaceItemDecoration(projectRecycleView.dp2px(10f)))
            adapter = projectAdapter
        }
    }

    private fun loadMore() {
        cid?.let {
            mPresenter.getProjectTypeDetailList(currentPage, it)
        }
    }

    override fun initData() {
    }

    override fun getProjectTypeList(projectTypeList: List<SystemParent>) {

    }

    override fun getProjectTypeDetailList(articleList: ArticleList) {
        projectAdapter.run {
            if (articleList.offset >= articleList.total) {
                loadMoreEnd()
                return
            }

            if (projectRefreshLayout.isRefreshing) replaceData(articleList.datas)
            else addData(articleList.datas)
            setEnableLoadMore(true)
            loadMoreComplete()
        }
        projectRefreshLayout.isRefreshing = false
        currentPage++
    }
}
package luyao.wanandroid.ui.search

import androidx.lifecycle.Observer
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import dp2px
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.title_layout.*
import luyao.base.BaseActivity
import luyao.wanandroid.R
import luyao.wanandroid.adapter.HomeArticleAdapter
import luyao.wanandroid.model.bean.ArticleList
import luyao.wanandroid.model.bean.Hot
import luyao.wanandroid.ui.BrowserNormalActivity
import luyao.wanandroid.ui.login.LoginActivity
import luyao.wanandroid.util.Preference
import luyao.wanandroid.view.CustomLoadMoreView
import luyao.wanandroid.view.SpaceItemDecoration


/**
 * Created by Lu
 * on 2018/4/2 22:00
 */
class SearchActivity : BaseActivity<SearchViewModel>() {

    override fun providerVMClass(): Class<SearchViewModel>? = SearchViewModel::class.java

    private val isLogin by Preference(Preference.IS_LOGIN, false)
    private lateinit var searchView: SearchView
    private val searchAdapter by lazy { HomeArticleAdapter() }
    private var currentPage = 0
    private var key = ""

    override fun getLayoutResId() = R.layout.activity_search

    private val hotList = mutableListOf<Hot>()
    private val webSitesList = mutableListOf<Hot>()

    override fun initView() {
        mToolbar.setNavigationIcon(R.drawable.arrow_back)
        initTagLayout()

        searchRecycleView.run {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            addItemDecoration(SpaceItemDecoration(searchRecycleView.dp2px(10f)))

        }
        initAdapter()
        searchRefreshLayout.setOnRefreshListener { refresh() }
    }

    private fun refresh() {
        searchAdapter.setEnableLoadMore(false)
        searchRefreshLayout.isRefreshing = true
        currentPage = 0
        mViewModel.searchHot(currentPage, key)
    }

    private fun initAdapter() {
        searchAdapter.run {
            setOnItemClickListener { _, _, position ->
                val intent = Intent(this@SearchActivity, BrowserNormalActivity::class.java)
                intent.putExtra(BrowserNormalActivity.URL, searchAdapter.data[position].link)
                startActivity(intent)
            }
            onItemChildClickListener = this@SearchActivity.onItemChildClickListener
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({ loadMore() }, homeRecycleView)
        }
        searchRecycleView.adapter = searchAdapter
        val emptyView = layoutInflater.inflate(R.layout.empty_view, searchRecycleView.parent as ViewGroup, false)
        val emptyTv = emptyView.findViewById<TextView>(R.id.emptyTv)
        emptyTv.text = getString(R.string.try_another_key)
        searchAdapter.emptyView = emptyView
    }

    private fun loadMore() {
        mViewModel.searchHot(currentPage, key)
    }

    override fun initData() {
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        mViewModel.getHotSearch()
        mViewModel.getWebSites()
    }

    private val onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
        when (view.id) {
            R.id.articleStar -> {
                if (isLogin) {
                    searchAdapter.run {
                        data[position].run {
                            collect = !collect
                            mViewModel.collectArticle(id,collect)
                        }
                        notifyDataSetChanged()
                    }
                } else {
                    Intent(this, LoginActivity::class.java).run { startActivity(this) }
                }
            }
        }
    }


    private fun initTagLayout() {
        hotTagLayout.run {
            adapter = object : TagAdapter<Hot>(hotList) {
                override fun getCount() = hotList.size

                override fun getView(parent: FlowLayout, position: Int, t: Hot): View {
                    val tv = LayoutInflater.from(parent.context).inflate(R.layout.tag,
                            parent, false) as TextView
                    tv.text = t.name
                    return tv
                }
            }

            setOnTagClickListener { _, position, _ ->
                key = hotList[position].name
                startSearch(key)
                true
            }
        }

        webTagLayout.run {
            adapter = object : TagAdapter<Hot>(webSitesList) {
                override fun getCount() = webSitesList.size

                override fun getView(parent: FlowLayout, position: Int, t: Hot): View {
                    val tv = LayoutInflater.from(parent.context).inflate(R.layout.tag,
                            parent, false) as TextView
                    tv.text = t.name
                    return tv
                }
            }

            setOnTagClickListener { _, position, parent ->
                val intent = Intent(parent.context, BrowserNormalActivity::class.java)
                intent.putExtra(BrowserNormalActivity.URL, webSitesList[position].link)
                parent.context.startActivity(intent)
                true
            }
        }
    }

    private fun startSearch(key: String) {
        searchView.clearFocus()
        searchRefreshLayout.isRefreshing = true
        currentPage = 0
        mViewModel.searchHot(0, key)
        hotContent.visibility = View.GONE
        searchRecycleView.visibility = View.VISIBLE
    }

    private fun searchHot(articleList: ArticleList) {
        searchAdapter.run {

            if (articleList.datas.isEmpty()) {
                loadMoreEnd()
                return
            }
            if (articleList.offset >= articleList.total) {
                loadMoreEnd()
                return
            }

            if (searchRefreshLayout.isRefreshing) replaceData(articleList.datas)
            else addData(articleList.datas)
            setEnableLoadMore(true)
            loadMoreComplete()
        }
        searchRefreshLayout.isRefreshing = false
        currentPage++
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_searchview, menu)
        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.run {
            isIconified = false
            onActionViewExpanded()
            setOnQueryTextListener(onQueryTextListener)
        }
        return super.onCreateOptionsMenu(menu)
    }

    private val onQueryTextListener = object : SearchView.OnQueryTextListener {

        override fun onQueryTextChange(newText: String?) = false

        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let {
                key = query
                startSearch(key)
            }
            return true
        }
    }

    override fun onBackPressed() {
        if (hotContent.visibility == View.GONE) {
            hotContent.visibility = View.VISIBLE
            searchRecycleView.visibility = View.GONE
            searchAdapter.setNewData(null)
        } else
            finish()
    }

    override fun startObserve() {
        mViewModel.apply {
            mArticleList.observe(this@SearchActivity, Observer {
                it?.run { searchHot(it) }
            })

            mWebSiteHot.observe(this@SearchActivity, Observer {
                it?.run {
                    webSitesList.clear()
                    webSitesList.addAll(it)
                    webTagLayout.adapter.notifyDataChanged()
                }
            })

            mHotSearch.observe(this@SearchActivity, Observer {
                it?.run {
                    hotList.clear()
                    hotList.addAll(it)
                    hotTagLayout.adapter.notifyDataChanged()
                }
            })
        }
    }
}
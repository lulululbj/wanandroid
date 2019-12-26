package luyao.wanandroid.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_home.*
import luyao.util.ktx.base.BaseVMFragment
import luyao.util.ktx.ext.dp2px
import luyao.util.ktx.ext.startKtxActivity
import luyao.wanandroid.R
import luyao.wanandroid.adapter.HomeArticleAdapter
import luyao.wanandroid.model.bean.Hot
import luyao.wanandroid.ui.BrowserActivity
import luyao.wanandroid.ui.login.LoginActivity
import luyao.wanandroid.util.Preference
import luyao.wanandroid.view.CustomLoadMoreView
import luyao.wanandroid.view.SpaceItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Created by Lu
 * on 2018/4/2 22:00
 */
class SearchFragment : BaseVMFragment<SearchViewModel>() {

    private val mViewModel: SearchViewModel by viewModel()

    private val isLogin by Preference(Preference.IS_LOGIN, false)
    private val searchAdapter by lazy { HomeArticleAdapter() }
    private var key = ""

    override fun getLayoutResId() = R.layout.activity_search

    private val hotList = mutableListOf<Hot>()
    private val webSitesList = mutableListOf<Hot>()

    override fun initView() {
        initTagLayout()

        searchRecycleView.run {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceItemDecoration(searchRecycleView.dp2px(10)))

        }
        initAdapter()
        searchRefreshLayout.setOnRefreshListener { refresh() }

        searchView.run {
//            isIconified = false
//            onActionViewExpanded()
            setOnQueryTextListener(onQueryTextListener)
        }
    }

    private fun refresh() {
        searchAdapter.setEnableLoadMore(false)
        mViewModel.searchHot(true, key)
    }

    private fun initAdapter() {
        searchAdapter.run {
            setOnItemClickListener { _, _, position ->
                Navigation.findNavController(searchRecycleView).navigate(R.id.action_tab_to_browser, bundleOf(BrowserActivity.URL to searchAdapter.data[position].link))
            }
            onItemChildClickListener = this@SearchFragment.onItemChildClickListener
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
        mViewModel.searchHot(false, key)
    }

    override fun initData() {
//        searchToolbar.setNavigationOnClickListener { onBackPressed() }
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
                            mViewModel.collectArticle(id, collect)
                        }
                        notifyDataSetChanged()
                    }
                } else {
                    startKtxActivity<LoginActivity>()
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
                parent.context.startKtxActivity<BrowserActivity>(value = BrowserActivity.URL to webSitesList[position].link)
                true
            }
        }
    }

    private fun startSearch(key: String) {
        searchView.clearFocus()
        mViewModel.searchHot(true, key)
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

    override fun startObserve() {

        mViewModel.uiState.observe(this, Observer {
            searchRecycleView.visibility = if (it.showHot) View.GONE else View.VISIBLE
            hotContent.visibility = if (!it.showHot) View.GONE else View.VISIBLE
            searchRefreshLayout.isRefreshing = it.showLoading

            it.showSuccess?.let { list ->
                searchAdapter.run {
                    if (it.isRefresh) replaceData(list.datas)
                    else addData(list.datas)
                    setEnableLoadMore(true)
                    loadMoreComplete()
                }
            }

            if (it.showEnd) searchAdapter.loadMoreEnd()

            it.showHotSearch?.let { data ->
                hotList.clear()
                hotList.addAll(data)
                hotTagLayout.adapter.notifyDataChanged()
            }

            it.showWebSites?.let { data ->
                webSitesList.clear()
                webSitesList.addAll(data)
                webTagLayout.adapter.notifyDataChanged()
            }

        })

    }
}

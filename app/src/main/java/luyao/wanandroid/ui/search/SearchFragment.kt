package luyao.wanandroid.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search.*
import luyao.util.ktx.ext.startKtxActivity
import luyao.wanandroid.R
import luyao.wanandroid.adapter.HomeArticleAdapter
import luyao.wanandroid.databinding.FragmentSearchBinding
import luyao.wanandroid.model.bean.Hot
import luyao.wanandroid.ui.BrowserActivity
import luyao.wanandroid.util.Preference
import luyao.wanandroid.view.CustomLoadMoreView


/**
 * Created by Lu
 * on 2018/4/2 22:00
 */
@AndroidEntryPoint
class SearchFragment : luyao.mvvm.core.base.BaseVMFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val searchViewModel : SearchViewModel by viewModels()

    private val isLogin by Preference(Preference.IS_LOGIN, false)
    private val searchAdapter by lazy { HomeArticleAdapter() }
    private var key = ""
    private lateinit var mEmptyView: View

    private val hotList = mutableListOf<Hot>()
    private val webSitesList = mutableListOf<Hot>()

    override fun initView() {
        binding.run {
            viewModel = searchViewModel
            adapter = searchAdapter
        }
        initTagLayout()
        initAdapter()
        searchRefreshLayout.setOnRefreshListener { refresh() }

        searchView.run {
            setOnQueryTextListener(onQueryTextListener)
        }
    }

    private fun refresh() {
        searchAdapter.setEnableLoadMore(false)
        searchViewModel.searchHot(true, key)
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
        mEmptyView = layoutInflater.inflate(R.layout.empty_view, searchRecycleView.parent as ViewGroup, false)
        val emptyTv = mEmptyView.findViewById<TextView>(R.id.emptyTv)
        emptyTv.text = getString(R.string.try_another_key)
    }

    private fun loadMore() {
        searchViewModel.searchHot(false, key)
    }

    override fun initData() {
//        searchToolbar.setNavigationOnClickListener { onBackPressed() }
        searchViewModel.getHotSearch()
        searchViewModel.getWebSites()
    }

    private val onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
        when (view.id) {
            R.id.articleStar -> {
                if (isLogin) {
                    searchAdapter.run {
                        data[position].run {
                            collect = !collect
                            searchViewModel.collectArticle(id, collect)
                        }
                        notifyDataSetChanged()
                    }
                } else {
                    Navigation.findNavController(searchRecycleView).navigate(R.id.action_tab_to_login)
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
        searchViewModel.searchHot(true, key)
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

        searchViewModel.uiState.observe(viewLifecycleOwner, Observer {
            searchRecycleView.visibility = if (it.showHot) View.GONE else View.VISIBLE
            hotContent.visibility = if (!it.showHot) View.GONE else View.VISIBLE
            searchRefreshLayout.isRefreshing = it.showLoading

            it.showSuccess?.let { list ->
                searchAdapter.run {
                    emptyView = mEmptyView
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

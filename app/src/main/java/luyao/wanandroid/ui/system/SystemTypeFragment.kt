package luyao.wanandroid.ui.system

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_systemtype.*
import luyao.util.ktx.ext.dp2px
import luyao.util.ktx.ext.toast
import luyao.wanandroid.R
import luyao.wanandroid.adapter.HomeArticleAdapter
import luyao.wanandroid.databinding.FragmentSystemtypeBinding
import luyao.wanandroid.ui.BrowserActivity
import luyao.wanandroid.ui.square.ArticleViewModel
import luyao.wanandroid.util.Preference
import luyao.wanandroid.view.CustomLoadMoreView
import luyao.wanandroid.view.SpaceItemDecoration
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * 体系下文章列表
 * Created by Lu
 * on 2018/3/27 21:36
 */
class SystemTypeFragment : luyao.mvvm.core.base.BaseVMFragment<ArticleViewModel>() {

    private val isLogin by Preference(Preference.IS_LOGIN, false)

    override fun initVM(): ArticleViewModel = getViewModel()

    private val cid by lazy { arguments?.getInt(CID) }
    private val isBlog by lazy { arguments?.getBoolean(BLOG) ?: false } // 区分是体系下的文章列表还是公众号下的文章列表
    private val systemTypeAdapter by lazy { HomeArticleAdapter() }

    companion object {
        private const val CID = "cid"
        private const val BLOG = "blog"
        fun newInstance(cid: Int, isBlog: Boolean): SystemTypeFragment {
            val fragment = SystemTypeFragment()
            val bundle = Bundle()
            bundle.putBoolean(BLOG, isBlog)
            bundle.putInt(CID, cid)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutResId() = R.layout.fragment_systemtype

    override fun initView() {
        (mBinding as FragmentSystemtypeBinding).viewModel = mViewModel
        initRecycleView()
    }

    override fun initData() {
        refresh()
    }

    private fun initRecycleView() {
        typeRefreshLayout.setOnRefreshListener { refresh() }
        systemTypeAdapter.run {
            setOnItemClickListener { _, _, position ->
                Navigation.findNavController(typeRecycleView).navigate(R.id.action_tab_to_browser, bundleOf(BrowserActivity.URL to systemTypeAdapter.data[position].link))
            }
            onItemChildClickListener = this@SystemTypeFragment.onItemChildClickListener

            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({ loadMore() }, typeRecycleView)
        }
        typeRecycleView.run {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceItemDecoration(typeRecycleView.dp2px(10)))
            adapter = systemTypeAdapter
        }
    }

    private val onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
        when (view.id) {
            R.id.articleStar -> {
                if (isLogin) {
                    systemTypeAdapter.run {
                        data[position].run {
                            collect = !collect
                            mViewModel.collectArticle(id, collect)
                        }
                        notifyDataSetChanged()
                    }
                } else {
                    Navigation.findNavController(typeRecycleView).navigate(R.id.action_tab_to_login)
                }
            }
        }
    }

    private fun loadMore() {
        loadData(false)
    }

    private fun refresh() {
        systemTypeAdapter.setEnableLoadMore(false)
        loadData(true)
    }


    private fun loadData(isRefresh: Boolean) {
        cid?.let {
            if (this.isBlog)
                mViewModel.getBlogArticleList(isRefresh, it)
            else
                mViewModel.getSystemTypeArticleList(isRefresh, it)
        }
    }

    override fun startObserve() {
        mViewModel.uiState.observe(this, Observer {
            typeRefreshLayout.isRefreshing = it.showLoading

            it.showSuccess?.let { list ->
                systemTypeAdapter.run {
                    if (it.isRefresh) replaceData(list.datas)
                    else addData(list.datas)
                    setEnableLoadMore(true)
                    loadMoreComplete()
                }
            }

            if (it.showEnd) systemTypeAdapter.loadMoreEnd()

            it.showError?.let { message ->
                activity?.toast(if (message.isBlank()) "网络异常" else message)
            }
        })
    }

}
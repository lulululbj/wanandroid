package luyao.wanandroid.ui.system

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_systemtype.*
import luyao.util.ktx.base.BaseVMFragment
import luyao.util.ktx.ext.dp2px
import luyao.util.ktx.ext.startKtxActivity
import luyao.wanandroid.R
import luyao.wanandroid.adapter.HomeArticleAdapter
import luyao.wanandroid.model.bean.ArticleList
import luyao.wanandroid.ui.BrowserNormalActivity
import luyao.wanandroid.ui.login.LoginActivity
import luyao.wanandroid.util.Preference
import luyao.wanandroid.view.CustomLoadMoreView
import luyao.wanandroid.view.SpaceItemDecoration
import onNetError

/**
 * Created by Lu
 * on 2018/3/27 21:36
 */
class SystemTypeFragment : BaseVMFragment<SystemViewModel>() {

    private val isLogin by Preference(Preference.IS_LOGIN, false)

    override fun providerVMClass(): Class<SystemViewModel>? = SystemViewModel::class.java

    private val cid by lazy { arguments?.getInt(CID) }
    private val isBlog by lazy { arguments?.getBoolean(BLOG) } // 区分是体系下的文章列表还是公众号下的文章列表
    private val systemTypeAdapter by lazy { HomeArticleAdapter() }
    private var currentPage = 0

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
                startKtxActivity<BrowserNormalActivity>(value = BrowserNormalActivity.URL to systemTypeAdapter.data[position].link)
            }
            onItemChildClickListener = this@SystemTypeFragment.onItemChildClickListener

            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({ loadMore() }, typeRecycleView)
        }
        typeRecycleView.run {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceItemDecoration(typeRecycleView.dp2px(10f)))
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
                    Intent(activity, LoginActivity::class.java).run { startActivity(this) }
                }
            }
        }
    }

    private fun loadMore() {
        cid?.let {
            if (this.isBlog!!)
                mViewModel.getBlogArticle(it, currentPage)
            else
                mViewModel.getSystemTypeDetail(it, currentPage)
        }
    }

    private fun refresh() {
        systemTypeAdapter.setEnableLoadMore(false)
        typeRefreshLayout.isRefreshing = true
        currentPage = 0
        loadMore()
    }

    override fun initData() {
    }

    private fun getSystemTypeDetail(articleList: ArticleList) {
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

    override fun startObserve() {
        super.startObserve()
        mViewModel.run {
            mArticleList.observe(this@SystemTypeFragment, Observer {
                it?.run { getSystemTypeDetail(it) }
            })
        }
    }

    override fun onError(e: Throwable) {
        activity?.onNetError(e){
            typeRefreshLayout.isRefreshing=false
        }
    }
}
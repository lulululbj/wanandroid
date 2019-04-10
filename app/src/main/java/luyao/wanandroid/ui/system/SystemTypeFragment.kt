package luyao.wanandroid.ui.system

import androidx.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import dp2px
import kotlinx.android.synthetic.main.fragment_systemtype.*
import luyao.wanandroid.R
import luyao.wanandroid.adapter.HomeArticleAdapter
import luyao.base.BaseFragment
import luyao.wanandroid.bean.ArticleList
import luyao.wanandroid.bean.SystemParent
import luyao.wanandroid.ui.BrowserActivity
import luyao.wanandroid.ui.login.LoginActivity
import luyao.wanandroid.util.Preference
import luyao.wanandroid.view.CustomLoadMoreView
import luyao.wanandroid.view.SpaceItemDecoration

/**
 * Created by Lu
 * on 2018/3/27 21:36
 */
class SystemTypeFragment : BaseFragment<SystemViewModel>(){
    private val isLogin by Preference(Preference.IS_LOGIN, false)

    override fun providerVMClass(): Class<SystemViewModel>? =SystemViewModel::class.java

    private val cid by lazy { arguments?.getInt(CID) }
    private val systemTypeAdapter by lazy { HomeArticleAdapter() }
    private var currentPage = 0

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
            onItemChildClickListener = this@SystemTypeFragment.onItemChildClickListener

            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({ loadMore() }, typeRecycleView)
        }
        typeRecycleView.run {
            layoutManager = LinearLayoutManager(activity)
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
                            mViewModel.collectArticle(id,collect)
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
            mViewModel.getSystemTypeDetail(it, currentPage)
        }
    }

    private fun refresh() {
        systemTypeAdapter.setEnableLoadMore(false)
        typeRefreshLayout.isRefreshing = true
        currentPage = 0
        cid?.let {
            mViewModel.getSystemTypeDetail(it, currentPage)
        }
    }

    override fun initData() {
    }

     fun getSystemTypeDetail(articleList: ArticleList) {
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
        mViewModel.run {
            mArticleList.observe(this@SystemTypeFragment, Observer {
                it?.run { getSystemTypeDetail(it) }
            })
        }
    }
}
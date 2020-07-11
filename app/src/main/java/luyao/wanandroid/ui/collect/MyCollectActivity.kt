package luyao.wanandroid.ui.collect

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_collect.*
import kotlinx.android.synthetic.main.title_layout.*
import luyao.mvvm.core.base.BaseVMActivity
import luyao.mvvm.core.view.SpaceItemDecoration
import luyao.util.ktx.ext.dp
import luyao.util.ktx.ext.startKtxActivity
import luyao.util.ktx.ext.toast
import luyao.wanandroid.R
import luyao.wanandroid.adapter.HomeArticleAdapter
import luyao.wanandroid.databinding.ActivityCollectBinding
import luyao.wanandroid.ui.BrowserActivity
import luyao.wanandroid.ui.square.ArticleViewModel
import luyao.wanandroid.view.CustomLoadMoreView
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Lu
 * on 2018/4/10 22:09
 */
class MyCollectActivity : BaseVMActivity() {

    private val articleViewModel by viewModel<ArticleViewModel>()
    private val binding by binding<ActivityCollectBinding>(R.layout.activity_collect)

    private val articleAdapter by lazy { HomeArticleAdapter() }

    override fun initView() {

        binding.viewModel = articleViewModel

        mToolbar.title = getString(R.string.my_collect)
        mToolbar.setNavigationIcon(R.drawable.arrow_back)

        collectRecycleView.run {
            layoutManager = LinearLayoutManager(this@MyCollectActivity)
            addItemDecoration(SpaceItemDecoration(bottom = 20.dp.toInt()))
        }

        initAdapter()

    }

    override fun initData() {
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        refresh()
    }

    private fun refresh() {
        articleViewModel.getCollectArticleList(true)
    }

    private fun initAdapter() {
        articleAdapter.run {
            //            showStar(false)
            setOnItemClickListener { _, _, position ->
                startKtxActivity<BrowserActivity>(value = BrowserActivity.URL to articleAdapter.data[position].link)
//                Navigation.findNavController(collectRecycleView).navigate(R.id.action_collect_to_browser, bundleOf(BrowserActivity.URL to articleAdapter.data[position].link))
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
                        articleViewModel.collectArticle(originId, collect)
                    }
                    notifyItemRemoved(position)
                }
            }
        }
    }

    private fun loadMore() {
        articleViewModel.getCollectArticleList(false)
    }

    override fun startObserve() {

        articleViewModel.apply {

            uiState.observe(this@MyCollectActivity, Observer {

                it.showSuccess?.let { list ->
                    articleAdapter.setEnableLoadMore(false)
                    list.datas.forEach { it.collect = true }
                    articleAdapter.run {
                        if (it.isRefresh) replaceData(list.datas)
                        else addData(list.datas)
                        setEnableLoadMore(true)
                        loadMoreComplete()
                    }
                }

                if (it.showEnd) articleAdapter.loadMoreEnd()

                it.showError?.let { message ->
                    toast(if (message.isBlank()) "网络异常" else message)
                }
            })
        }
    }

}
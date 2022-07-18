package luyao.wanandroid.ui.home

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.youth.banner.BannerConfig
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import luyao.mvvm.core.base.BaseVMFragment
import luyao.util.ktx.ext.dp2px
import luyao.util.ktx.ext.toast
import luyao.wanandroid.R
import luyao.wanandroid.adapter.HomeArticleAdapter
import luyao.wanandroid.databinding.FragmentHomeBinding
import luyao.wanandroid.model.bean.Banner
import luyao.wanandroid.ui.BrowserActivity
import luyao.wanandroid.ui.square.ArticleViewModel
import luyao.wanandroid.util.GlideImageLoader
import luyao.wanandroid.util.Preference
import luyao.wanandroid.view.CustomLoadMoreView


/**
 * Created by luyao
 * on 2018/3/13 14:15
 */
@AndroidEntryPoint
class HomeFragment : BaseVMFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val articleViewModel: ArticleViewModel by viewModels()

    private val isLogin by Preference(Preference.IS_LOGIN, false)
    private val homeArticleAdapter by lazy { HomeArticleAdapter() }
    private val bannerImages = mutableListOf<String>()
    private val bannerTitles = mutableListOf<String>()
    private val bannerUrls = mutableListOf<String>()
    private val banner by lazy { com.youth.banner.Banner(activity) }

    override fun initView() {
        binding.run {
            viewModel = articleViewModel
            adapter = homeArticleAdapter
        }
        initRecycleView()
        initBanner()
    }

    override fun initData() {
        refresh()
    }

    private fun initRecycleView() {
        homeArticleAdapter.run {
            setOnItemClickListener { _, _, position ->
                val bundle = bundleOf(BrowserActivity.URL to homeArticleAdapter.data[position].link)
                NavHostFragment.findNavController(this@HomeFragment)
                    .navigate(R.id.action_tab_to_browser, bundle)
            }
            onItemChildClickListener = this@HomeFragment.onItemChildClickListener
            if (headerLayoutCount > 0) removeAllHeaderView()
            addHeaderView(banner)
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({ loadMore() }, homeRecycleView)
        }
    }

    private val onItemChildClickListener =
        BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.articleStar -> {
                    if (isLogin) {
                        homeArticleAdapter.run {
                            data[position].run {
                                collect = !collect
                                articleViewModel.collectArticle(id, collect)
                            }
                            notifyDataSetChanged()
                        }
                    } else {
                        Navigation.findNavController(homeRecycleView)
                            .navigate(R.id.action_tab_to_login)
                    }
                }
            }
        }

    private fun loadMore() {
        articleViewModel.getHomeArticleList(false)
    }

    private fun initBanner() {

        banner.run {
            layoutParams =
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, banner.dp2px(200))
            setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
            setImageLoader(GlideImageLoader())
            setOnBannerListener { position ->
                run {
                    Navigation.findNavController(banner).navigate(
                        R.id.action_tab_to_browser,
                        bundleOf(BrowserActivity.URL to bannerUrls[position])
                    )
                }
            }
        }
    }

    fun refresh() {
        articleViewModel.getHomeArticleList(true)
    }

    override fun startObserve() {
        articleViewModel.apply {
            mBanners.observe(viewLifecycleOwner, Observer { it ->
                it?.let { setBanner(it) }
            })

            uiState.observe(viewLifecycleOwner, Observer {

                it.showSuccess?.let { list ->
                    homeArticleAdapter.run {
                        homeArticleAdapter.setEnableLoadMore(false)
                        if (it.isRefresh) replaceData(list)
                        else addData(list)
                        setEnableLoadMore(true)
                        loadMoreComplete()
                    }
                }

                if (it.showEnd) homeArticleAdapter.loadMoreEnd()

                it.showError?.let { message ->
                    activity?.toast(if (message.isBlank()) "网络异常" else message)
                }

            })
        }
    }

    private fun setBanner(bannerList: List<Banner>) {
        for (banner in bannerList) {
            bannerImages.add(banner.imagePath)
            bannerTitles.add(banner.title)
            bannerUrls.add(banner.url)
        }
        banner.setImages(bannerImages)
            .setBannerTitles(bannerTitles)
            .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
            .setDelayTime(3000)
        banner.start()
    }

    override fun onStart() {
        super.onStart()
        banner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        banner.stopAutoPlay()
    }

}